package pl.kartven.javaprobackend.infra.restapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.infra.restapi.dto.SlideResDto;
import pl.kartven.javaprobackend.infra.restapi.dto.SlidesReqDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface TopicSlideDefinition {
    @GetMapping("/{id}/slides")
    ResponseEntity<List<SlideResDto>> getSlidesOfTopic(
            @PathVariable Long id
    );

    @PostMapping("/{id}/slides")
    ResponseEntity<?> postSlidesOfTopic(
            @PathVariable Long id,
            @ModelAttribute SlidesReqDto body,
            @RequestParam("slides") List<MultipartFile> slides,
            HttpServletRequest request
    );
}
