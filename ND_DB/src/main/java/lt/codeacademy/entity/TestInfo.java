package lt.codeacademy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "testInfo")
public class TestInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int grade;
    private int correctAnswers;
    private int wrongAnswers;
    private LocalDateTime startDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "test_id")
    private Test test;

    public TestInfo(int grade, int correctAnswers, int wrongAnswers, LocalDateTime startDate) {
        this.grade = grade;
        this.correctAnswers = correctAnswers;
        this.wrongAnswers = wrongAnswers;
        this.startDate = startDate;
    }
}
