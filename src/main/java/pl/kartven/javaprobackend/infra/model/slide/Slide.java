package pl.kartven.javaprobackend.infra.model.slide;

import lombok.*;
import pl.kartven.javaprobackend.infra.model.topic.Topic;

import javax.persistence.*;

@Entity(name = "slides")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private byte[] imageBytes;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
}

