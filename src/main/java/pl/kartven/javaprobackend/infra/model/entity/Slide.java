package pl.kartven.javaprobackend.infra.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Slide(String title, byte[] imageBytes, Topic topic) {
        this.title = title;
        this.imageBytes = imageBytes;
        this.topic = topic;
    }
}

