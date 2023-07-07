package pl.kartven.javaprobackend.infra.restapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kartven.javaprobackend.infra.restapi.dto.QuestionDto;
import pl.kartven.javaprobackend.infra.restapi.dto.QuizDetailsDto;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("{id}/questions")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<QuestionDto>> getQuestionsOfQuiz(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(quizService.getQuestionsOfQuiz(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<QuizDetailsDto> getQuizDetails(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(quizService.getQuizDetails(id));
    }
}
