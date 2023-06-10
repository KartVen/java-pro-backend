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

    public static TopicDto fromEntity(Topic topic) {
        return new TopicDto(topic.getId(), topic.getName(), topic.getDescription());
    }

    public static Topic toEntity(TopicDto topicDto) {
        return new Topic(topicDto.getId(), topicDto.getName(), topicDto.getDescription());
    }
}