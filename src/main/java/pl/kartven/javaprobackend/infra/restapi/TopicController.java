package pl.kartven.javaprobackend.infra.restapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.infra.restapi.dto.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/topics")
public class TopicController implements TopicControllerDefinition {
    private final TopicService topicService;

    @Override
    public ResponseEntity<List<TopicResDto>> getTopics(Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getTopics(userId));
    }

    @Override
    public ResponseEntity<TopicResDto> postTopic(TopicReqDto body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.postTopic(body));
    }

    @Override
    public ResponseEntity<List<SectionResDto>> getSectionsOfTopic(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getSectionsOfTopic(id));
    }

    @Override
    public ResponseEntity<Void> postSectionOfTopic(Long id, SectionReqDto body) {
        topicService.postSectionOfTopic(id, body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<QuizResDto>> getQuizzesOfTopic(
            Long id, Long userId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getQuizzesOfTopic(id, userId));
    }

    @Override
    public ResponseEntity<?> postQuizOfTopic(Long id, QuizReqDto body) {
        topicService.postQuizOfTopic(id, body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<SlideResDto>> getSlidesOfTopic(Long id, Integer page, Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getSlidesOfTopic(id, page, size));
    }

    @Override
    public ResponseEntity<?> postSlidesOfTopic(Long id, SlidesReqDto body, List<MultipartFile> slides
    ) {
        topicService.postSlidesOfTopic(id, body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<?> postCodeOfTopic(Long id, CodeReqDto body) {
        topicService.postCodesOfTopic(id, body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<?> postLinkOfTopic(Long id, LinkReqDto body) {
        topicService.postLinkOfTopic(id, body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
