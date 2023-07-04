package pl.kartven.javaprobackend.infra.restapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.infra.restapi.dto.*;

import java.util.List;

@RequiredArgsConstructor
public class TopicController implements TopicControllerDefinition {
    private final TopicService topicService;

    @Override
    public ResponseEntity<List<TopicResDto>> getTopics(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getTopics(id));
    }

    @Override
    public ResponseEntity<Void> postTopic(TopicReqDto body) {
        topicService.postTopic(body);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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
    public ResponseEntity<List<QuizDto>> getQuizzesOfTopic(
            Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(topicService.getQuizzesOfTopic(id));
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
}
