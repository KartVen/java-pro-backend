package pl.kartven.javaprobackend.lecture.slide;

import lombok.Getter;
import lombok.Setter;
import pl.kartven.javaprobackend.lecture.Lecture;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "slides")
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    private byte[] content;
    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;
}