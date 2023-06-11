package pl.kartven.javaprobackend.infra.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.kartven.javaprobackend.infra.model.topic.Topic;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicDto {
    private Long id;
    private String name;
    private String description;
}