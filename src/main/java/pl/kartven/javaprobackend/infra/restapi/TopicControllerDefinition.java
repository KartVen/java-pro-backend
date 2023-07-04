package pl.kartven.javaprobackend.infra.restapi;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.infra.restapi.dto.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public interface TopicControllerDefinition {
    @GetMapping
    public ResponseEntity<List<TopicResDto>> getTopics(
            @RequestParam(value = "user_id", required = false) Long id
    );

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<Void> postTopic(
            @RequestBody @Valid TopicReqDto body
    );

    @GetMapping("/{id}/sections")
    ResponseEntity<List<SectionResDto>> getSectionsOfTopic(
            @PathVariable Long id
            //@RequestParam(value = "type", required = true) Type type
    );

    @PostMapping("/{id}/sections")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<Void> postSectionOfTopic(
            @PathVariable Long id,
            @RequestBody @Valid SectionReqDto body
    );

    @GetMapping("/{id}/quizzes")
    ResponseEntity<List<QuizDto>> getQuizzesOfTopic(
            @PathVariable Long id
    );

    @GetMapping("/{id}/slides")
    ResponseEntity<List<SlideResDto>> getSlidesOfTopic(
            @PathVariable Long id,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size
    );

    @PostMapping("/{id}/slides")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<?> postSlidesOfTopic(
            @PathVariable Long id,
            @ModelAttribute @Valid SlidesReqDto body,
            @RequestParam("slides") List<MultipartFile> slides
    );
}
