package pl.kartven.javaprobackend.infra.restapi;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kartven.javaprobackend.exception.ServerProcessingException;
import pl.kartven.javaprobackend.infra.model.quiz.QuizRepository;
import pl.kartven.javaprobackend.infra.model.section.SectionRepository;
import pl.kartven.javaprobackend.infra.model.topic.TopicRepository;
import pl.kartven.javaprobackend.infra.restapi.dto.QuizDto;
import pl.kartven.javaprobackend.infra.restapi.dto.SectionDto;
import pl.kartven.javaprobackend.infra.restapi.dto.TopicDto;
import pl.kartven.javaprobackend.infra.restapi.mapper.QuizDetailsMapper;
import pl.kartven.javaprobackend.infra.restapi.mapper.QuizMapper;
import pl.kartven.javaprobackend.infra.restapi.mapper.SectionMapper;
import pl.kartven.javaprobackend.infra.restapi.mapper.TopicMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final SectionMapper sectionMapper;
    private final QuizMapper quizMapper;
    private final SectionRepository sectionRepository;
    private final QuizRepository quizRepository;

    public List<TopicDto> getTopics() {
        return Option.of(topicRepository.findAll())
                .map(topicMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }


    public List<SectionDto> getSectionsOfTopic(Long id) {
        return Option.of(sectionRepository.findByTopic_Id(id))
                .map(sectionMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }

    public List<QuizDto> getQuizzesOfTopic(Long id) {
        return Option.of(quizRepository.findByTopic_Id(id))
                .map(quizMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }
}
