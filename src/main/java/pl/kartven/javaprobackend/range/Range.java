package pl.kartven.javaprobackend.range;

import lombok.Getter;
import lombok.Setter;
import pl.kartven.javaprobackend.range.link.Link;
import pl.kartven.javaprobackend.range.test.question.Question;
import pl.kartven.javaprobackend.range.laboratory.section.Section;
import pl.kartven.javaprobackend.range.slide.Slide;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "lectures")
public class Range {
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

    /*@ManyToMany
    @JoinTable(
            name = "range_types",
            joinColumns = @JoinColumn(name = "range_id"),
            inverseJoinColumns = @JoinColumn(name = "type_id")
    )
    private Set<Type> types;*/
}