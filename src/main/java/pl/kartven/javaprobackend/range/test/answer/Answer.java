package pl.kartven.javaprobackend.range.test.answer;

import lombok.Getter;
import lombok.Setter;
import pl.kartven.javaprobackend.range.test.question.Question;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String content;
    private boolean corrected;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}