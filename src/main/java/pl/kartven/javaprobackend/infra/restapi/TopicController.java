package pl.kartven.javaprobackend.infra.restapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kartven.javaprobackend.infra.restapi.dto.QuizDto;
import pl.kartven.javaprobackend.infra.restapi.dto.SectionDto;
import pl.kartven.javaprobackend.infra.restapi.dto.TopicDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/topics")
public class TopicController {
    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<TopicDto>> getTopics(){
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getTopics());
    }

    @GetMapping("/{id}/sections")
    public ResponseEntity<List<SectionDto>> getSectionsOfTopic(
            @PathVariable Long id
            //@RequestParam(value = "type", required = true) Type type
    ){
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getSectionsOfTopic(id));
    }

    @GetMapping("/{id}/quizzes")
    public ResponseEntity<List<QuizDto>> getQuizzesOfTopic(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getQuizzesOfTopic(id));
    }
}
