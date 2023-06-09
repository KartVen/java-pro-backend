package pl.kartven.javaprobackend.range.link;

import lombok.Getter;
import lombok.Setter;
import pl.kartven.javaprobackend.range.Range;

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
    private Range lecture;
    private String content;
}