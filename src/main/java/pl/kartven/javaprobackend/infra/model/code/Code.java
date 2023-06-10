package pl.kartven.javaprobackend.infra.model.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kartven.javaprobackend.infra.model.section.Section;
import pl.kartven.javaprobackend.infra.model.topic.Topic;

import javax.persistence.*;

@Entity(name = "codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
}
