package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.quiz.Quiz;
import pl.kartven.javaprobackend.infra.model.topic.Topic;
import pl.kartven.javaprobackend.infra.restapi.dto.QuizDto;
import pl.kartven.javaprobackend.infra.restapi.dto.TopicDto;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public abstract class QuizMapper {
    public abstract List<QuizDto> map(List<Quiz> quizzes);
    public abstract QuizDto map(Quiz quiz);
}
