package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import pl.kartven.javaprobackend.infra.model.entity.Question;
import pl.kartven.javaprobackend.infra.model.entity.Quiz;
import pl.kartven.javaprobackend.infra.restapi.dto.QuizDetailsDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuizDetailsMapper {
    @Mappings(value = {
            @Mapping(target = "creator", source = "user"),
            @Mapping(target = "questions", source = "questions")
    })
    QuizDetailsDto map(Quiz quiz);

    default Long map(List<Question> questions) {
        return (long) questions.size();
    }
}
