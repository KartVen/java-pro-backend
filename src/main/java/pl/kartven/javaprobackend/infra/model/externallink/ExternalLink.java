package pl.kartven.javaprobackend.infra.model.externallink;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kartven.javaprobackend.infra.model.section.Section;
import pl.kartven.javaprobackend.infra.model.topic.Topic;

import javax.persistence.*;

@Entity(name = "external_links")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;
    private String name;
    private String URL;
}
