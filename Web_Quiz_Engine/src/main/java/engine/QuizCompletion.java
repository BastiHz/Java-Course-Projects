package engine;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class QuizCompletion {

    @Column(name = "quiz_id")
    private int id;  // I would call it "quizId" but the tests want "id".

    @Id
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public QuizCompletion() {}

    public QuizCompletion(final int id, final User user) {
        this.id = id;
        this.user = user;
        this.completedAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }
}
