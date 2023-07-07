package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import pl.kartven.javaprobackend.infra.model.entity.Answer;
import pl.kartven.javaprobackend.infra.restapi.dto.QuestionDto;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
interface AnswerMapper {
    List<QuestionDto.Answer> map(Set<Answer> answers);

    QuestionDto.Answer map(Answer answer);
}
