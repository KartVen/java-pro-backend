package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import pl.kartven.javaprobackend.infra.model.entity.Question;
import pl.kartven.javaprobackend.infra.restapi.dto.QuestionDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        AnswerMapper.class
})
public interface QuestionMapper {
    List<QuestionDto> map(List<Question> questions);

    QuestionDto map(Question question);
}
