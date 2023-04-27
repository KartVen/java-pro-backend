package pl.kartven.javaprobackend.lecture;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kartven.javaprobackend.rest.LectureCreateRequest;
import pl.kartven.javaprobackend.rest.SingleLectureDetails;
import pl.kartven.javaprobackend.rest.SingleSlideDetails;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class LectureController {
    private final LectureService lectureService;

    @GetMapping
    public ResponseEntity<List<SingleLectureDetails>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(lectureService.getAllLectures());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SingleSlideDetails>> getAll(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(lectureService.getSlidesByLectureId(id));
    }
}
