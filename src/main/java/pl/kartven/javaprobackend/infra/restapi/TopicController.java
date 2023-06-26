package pl.kartven.javaprobackend.infra.restapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.infra.restapi.dto.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/topics")
public class TopicController implements TopicSlideDefinition {
    private final TopicService topicService;

    @GetMapping
    public ResponseEntity<List<TopicDto>> getTopics() {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getTopics());
    }

    @GetMapping("/{id}/sections")
    public ResponseEntity<List<SectionDto>> getSectionsOfTopic(
            @PathVariable Long id
            //@RequestParam(value = "type", required = true) Type type
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getSectionsOfTopic(id));
    }

    @GetMapping("/{id}/quizzes")
    public ResponseEntity<List<QuizDto>> getQuizzesOfTopic(
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getQuizzesOfTopic(id));
    }

    @Override
    public ResponseEntity<List<SlideResDto>> getSlidesOfTopic(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getSlidesOfTopic(id));
    }

    @Override
    public ResponseEntity<?> postSlidesOfTopic(
            Long id, SlidesReqDto body, List<MultipartFile> slides, HttpServletRequest request
    ) {
        topicService.postSlidesOfTopic(id, body);
        return ResponseEntity.created(URI.create(String.format("/api/topics/%d/slides", id))).build();
    }
}
