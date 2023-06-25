package pl.kartven.javaprobackend.infra.model.quiz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kartven.javaprobackend.infra.model.question.Question;
import pl.kartven.javaprobackend.infra.model.topic.Topic;

import javax.persistence.*;
import java.util.List;

@Entity(name = "quizzes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
    private String name;
    private String description;
    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;
}

