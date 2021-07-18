package engine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.*;

@RestController
@Validated
@RequestMapping(path = "/api/quizzes")
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompletionRepository completionRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    Quiz addQuiz(@RequestBody @Valid Quiz quiz, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        quiz.setUser(user);
        return quizRepository.save(quiz);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteQuiz(@PathVariable int id, Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        if (user.getId() != quiz.getUser().getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        quizRepository.delete(quiz);
    }

    @GetMapping
    Page<Quiz> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id"));
        return quizRepository.findAll(pageable);
    }

    @GetMapping(path = "/{id}")
    Quiz getQuiz(@PathVariable @Min(0) int id) {
        return quizRepository.findById(id).orElseThrow();
    }

    @GetMapping(path = "/completed")
    Page<QuizCompletion> getCompletions(
            @RequestParam(defaultValue = "0") Integer page,
            Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        Pageable pageable = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        return completionRepository.findByUser(user, pageable);
    }

    @PostMapping(path = "/{id}/solve")
    Response checkAnswer(
            @PathVariable @Min(0) int id,
            @RequestBody Answer answer, Principal principal) {
        Quiz quiz = quizRepository.findById(id).orElseThrow();
        User user = userRepository.findByEmail(principal.getName());
        boolean isCorrect = isAnswerCorrect(quiz.getAnswer(), answer.getAnswer());
        if (isCorrect) {
            completionRepository.save(new QuizCompletion(quiz.getId(), user));
            return Response.CORRECT_ANSWER;
        }
        return Response.WRONG_ANSWER;
    }

    private boolean isAnswerCorrect(List<Integer> expected, List<Integer> given) {
        if (expected == null && given == null) {
            return true;
        } else if (given == null) {
            given = new ArrayList<>();
        } else if (expected == null) {
            expected = new ArrayList<>();
        } else if (expected.size() != given.size()) {
            return false;
        }
        Collections.sort(expected);
        Collections.sort(given);
        // Iterate because expected might be a PersistentBag instead of a List.
        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).equals(given.get(i))) {
                return false;
            }
        }
        return true;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Id not found.")
    void handleWrongIdException() {}
}
