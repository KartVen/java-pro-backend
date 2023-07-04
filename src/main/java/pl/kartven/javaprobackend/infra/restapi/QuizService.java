package pl.kartven.javaprobackend.infra.restapi;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kartven.javaprobackend.exception.structure.NotFoundException;
import pl.kartven.javaprobackend.exception.structure.ServerProcessingException;
import pl.kartven.javaprobackend.infra.model.repository.QuestionRepository;
import pl.kartven.javaprobackend.infra.model.repository.QuizRepository;
import pl.kartven.javaprobackend.infra.restapi.dto.QuestionDto;
import pl.kartven.javaprobackend.infra.restapi.dto.QuizDetailsDto;
import pl.kartven.javaprobackend.infra.restapi.mapper.QuestionMapper;
import pl.kartven.javaprobackend.infra.restapi.mapper.QuizDetailsMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuizDetailsMapper quizDetailsMapper;
    private final QuizRepository quizRepository;

    public List<QuestionDto> getQuestionsOfQuiz(Long id) {
        return Option.of(questionRepository.findByQuiz_Id(id))
                .map(questionMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }

    public QuizDetailsDto getQuizDetails(Long id) {
        return Option.of(quizRepository.findById(id).orElseThrow(NotFoundException::new))
                .map(quizDetailsMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }
}
