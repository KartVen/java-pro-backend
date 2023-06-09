package pl.kartven.javaprobackend.range.slide;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kartven.javaprobackend.range.Range;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "slides")
@NoArgsConstructor
public class Slide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Lob
    private byte[] content;
    @ManyToOne
    @JoinColumn(name = "lecture_id")
    private Range lecture;

    public Slide(byte[] content) {
        this.content = content;
    }
}