package pl.kartven.javaprobackend.infra.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicReqDto {
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Description cannot be null")
    private String description;
}