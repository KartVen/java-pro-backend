package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.entity.Answer;
import pl.kartven.javaprobackend.infra.model.entity.Question;
import pl.kartven.javaprobackend.infra.model.entity.Quiz;
import pl.kartven.javaprobackend.infra.restapi.dto.QuizReqDto;
import pl.kartven.javaprobackend.infra.restapi.dto.QuizResDto;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface QuizMapper {

    List<QuizResDto> map(List<Quiz> quizzes);

    QuizResDto map(Quiz quiz);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "topic", ignore = true)
    @Mapping(target = "user", ignore = true)
    Quiz map(QuizReqDto quizReqDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quiz", ignore = true)
    Question map(QuizReqDto.QuestionDto questionDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "correct", source = "correct")
    Answer map(QuizReqDto.QuestionDto.AnswerDto questionDto);
}