package engine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface CompletionRepository extends JpaRepository<QuizCompletion, LocalDateTime> {

    Page<QuizCompletion> findByUser(User user, Pageable pageable);
}
