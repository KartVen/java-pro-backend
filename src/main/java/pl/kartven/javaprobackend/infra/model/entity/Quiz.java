package pl.kartven.javaprobackend.infra.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

