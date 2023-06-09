package pl.kartven.javaprobackend.range.laboratory.section;

import lombok.Getter;
import lombok.Setter;
import pl.kartven.javaprobackend.range.laboratory.code.Code;
import pl.kartven.javaprobackend.range.Range;

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
    private Range lecture;

    @OneToMany(mappedBy = "section")
    private List<Code> codes;

}