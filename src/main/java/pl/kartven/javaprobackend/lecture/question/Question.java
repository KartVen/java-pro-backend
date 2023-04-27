package pl.kartven.javaprobackend.lecture.question;

import lombok.Getter;
import lombok.Setter;
import pl.kartven.javaprobackend.lecture.Lecture;
import pl.kartven.javaprobackend.lecture.answer.Answer;

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
    private Lecture lecture;
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
}