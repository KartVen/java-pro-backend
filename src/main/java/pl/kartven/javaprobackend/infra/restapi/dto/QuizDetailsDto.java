package pl.kartven.javaprobackend.infra.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizDetailsDto extends QuizResDto {
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
