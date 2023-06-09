package pl.kartven.javaprobackend.range.test.question;

import lombok.Getter;
import lombok.Setter;
import pl.kartven.javaprobackend.range.Range;
import pl.kartven.javaprobackend.range.test.answer.Answer;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String content;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Range lecture;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
}