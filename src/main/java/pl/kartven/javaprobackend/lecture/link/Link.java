package pl.kartven.javaprobackend.lecture.link;

import lombok.Getter;
import lombok.Setter;
import pl.kartven.javaprobackend.lecture.Lecture;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "links")
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
    private String content;
}