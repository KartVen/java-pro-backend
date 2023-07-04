package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.entity.Quiz;
import pl.kartven.javaprobackend.infra.restapi.dto.QuizDto;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface QuizMapper {
    List<QuizDto> map(List<Quiz> quizzes);

    QuizDto map(Quiz quiz);
}
