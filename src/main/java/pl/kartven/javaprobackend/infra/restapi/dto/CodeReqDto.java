package pl.kartven.javaprobackend.infra.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeReqDto {
    @NotNull
    private String name;
    @NotNull
    private String content;
    @NotNull
    private SectionDto section;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SectionDto {
        private Long id;
        @NotNull
        private String name;
    }
}
