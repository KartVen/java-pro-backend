package pl.kartven.javaprobackend.lecture.section;

import lombok.Getter;
import lombok.Setter;
import pl.kartven.javaprobackend.lecture.code.Code;
import pl.kartven.javaprobackend.lecture.Lecture;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sections")
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @OneToMany(mappedBy = "section")
    private List<Code> codes;

}