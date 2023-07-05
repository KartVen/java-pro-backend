package pl.kartven.javaprobackend.infra.restapi;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.infra.restapi.dto.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/api/topics")
public interface TopicControllerDefinition {
    @GetMapping
    ResponseEntity<List<TopicResDto>> getTopics(
            @RequestParam(value = "userId", required = false) Long userId
    );

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<TopicResDto> postTopic(
            @RequestBody @Valid TopicReqDto body
    );

    @GetMapping("/{id}/sections")
    ResponseEntity<List<SectionResDto>> getSectionsOfTopic(
            @PathVariable Long id
    );

    @PostMapping("/{id}/sections")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<Void> postSectionOfTopic(
            @PathVariable Long id,
            @RequestBody @Valid SectionReqDto body
    );

    @GetMapping("/{id}/quizzes")
    ResponseEntity<List<QuizResDto>> getQuizzesOfTopic(
            @PathVariable Long id,
            @RequestParam(value = "user_id", required = false) Long userId
    );

    @PostMapping("/{id}/quizzes")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<?> postQuizOfTopic(
            @PathVariable Long id,
            @RequestBody QuizReqDto body
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

    @PostMapping("/{id}/codes")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<?> postCodeOfTopic(
            @PathVariable Long id,
            @RequestBody @Valid CodeReqDto body
    );

    @PostMapping("/{id}/links")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<?> postLinkOfTopic(
            @PathVariable Long id,
            @RequestBody @Valid LinkReqDto body
    );
}
