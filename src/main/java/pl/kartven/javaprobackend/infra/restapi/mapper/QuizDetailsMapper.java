package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.*;
import pl.kartven.javaprobackend.infra.model.question.Question;
import pl.kartven.javaprobackend.infra.model.quiz.Quiz;
import pl.kartven.javaprobackend.infra.restapi.dto.QuizDetailsDto;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuizDetailsMapper {
    @Mappings(value = {
            @Mapping(target = "creator", source = "quiz", qualifiedByName = "mapCreator"),
            @Mapping(target = "questions", source = "questions")
    })
    public abstract QuizDetailsDto map(Quiz quiz);

    protected Long map(List<Question> questions) {
        return (long) questions.size();
    }

    @Named("mapCreator")
    protected QuizDetailsDto.User mapCreator(Quiz quiz){
        QuizDetailsDto.User user = new QuizDetailsDto.User();
        user.setId(1L);
        user.setName("example_name");
        return user;
    }
}
