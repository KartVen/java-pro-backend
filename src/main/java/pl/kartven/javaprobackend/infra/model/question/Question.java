package pl.kartven.javaprobackend.infra.model.question;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kartven.javaprobackend.infra.model.answer.Answer;
import pl.kartven.javaprobackend.infra.model.quiz.Quiz;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @OneToMany(mappedBy = "question")
    private Set<Answer> answers = new HashSet<>();


    private String text;
}

