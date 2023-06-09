package pl.kartven.javaprobackend.range.laboratory.code;

import lombok.Getter;
import lombok.Setter;
import pl.kartven.javaprobackend.range.laboratory.section.Section;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "codes")
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
    private String content;
}