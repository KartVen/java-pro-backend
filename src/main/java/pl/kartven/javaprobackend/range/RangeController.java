package pl.kartven.javaprobackend.range;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.kartven.javaprobackend.restapi.SingleRangeDetails;
import pl.kartven.javaprobackend.restapi.SingleSlideDetails;

import java.util.List;

@RestController
@RequestMapping("/api/lectures")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class RangeController {
    private final RangeService rangeService;

    @GetMapping
    public ResponseEntity<List<SingleRangeDetails>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(rangeService.getRangeList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<SingleSlideDetails>> getAll(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(rangeService.getSlidesByRangeId(id));
    }
}
