package pl.kartven.javaprobackend.range.slide;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/slides")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class SlideController {
    private final SlideService slideService;

    @PostMapping
    public ResponseEntity<Slide> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.OK).body(slideService.uploadImage(file));
    }
}