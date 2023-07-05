package pl.kartven.javaprobackend.infra.restapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizReqDto {
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private List<QuestionDto> questions;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuestionDto {
        @NotNull
        private String text;
        @NotNull
        private List<AnswerDto> answers;

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class AnswerDto {
            @NotNull
            private String text;
            @NotNull
            @JsonProperty("isCorrect")
            private boolean isCorrect;
        }
    }
}