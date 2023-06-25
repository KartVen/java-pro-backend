package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.answer.Answer;
import pl.kartven.javaprobackend.infra.model.question.Question;
import pl.kartven.javaprobackend.infra.model.quiz.Quiz;
import pl.kartven.javaprobackend.infra.restapi.dto.QuestionDto;
import pl.kartven.javaprobackend.infra.restapi.dto.QuizDto;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {
        QuestionMapper.AnswerMapper.class
})
public abstract class QuestionMapper {
    public abstract List<QuestionDto> map(List<Question> questions);

    public abstract QuestionDto map(Question question);

    @Mapper(componentModel = "spring")
    public static abstract class AnswerMapper {
        public abstract List<QuestionDto.Answer> map(Set<Answer> answers);
        public abstract QuestionDto.Answer map(Answer answer);
    }
}
