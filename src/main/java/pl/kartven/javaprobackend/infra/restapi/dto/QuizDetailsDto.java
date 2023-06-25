package pl.kartven.javaprobackend.infra.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.kartven.javaprobackend.infra.model.topic.Topic;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDetailsDto extends QuizDto {
    private Topic topic;
    private Long questions;
    private User creator;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Topic {
        private Long id;
        private String name;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    @AllArgsConstructor
    public static class User extends UserDto {
    }
}
