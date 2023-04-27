package pl.kartven.javaprobackend.lecture;

import lombok.Getter;
import lombok.Setter;
import pl.kartven.javaprobackend.lecture.link.Link;
import pl.kartven.javaprobackend.lecture.question.Question;
import pl.kartven.javaprobackend.lecture.section.Section;
import pl.kartven.javaprobackend.lecture.slide.Slide;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "lectures")
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String topic;
    private String description;

    @OneToMany(mappedBy = "lecture")
    private List<Section> sections;

    @OneToMany(mappedBy = "lecture")
    private List<Question> questions;

    @OneToMany(mappedBy = "lecture")
    private List<Link> links;

    @OneToMany(mappedBy = "lecture")
    private List<Slide> slides;

}