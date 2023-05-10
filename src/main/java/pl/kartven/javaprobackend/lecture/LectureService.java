package pl.kartven.javaprobackend.lecture;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kartven.javaprobackend.exception.NotFoundException;
import pl.kartven.javaprobackend.exception.ServerProcessingException;
import pl.kartven.javaprobackend.http.SingleLectureDetails;
import pl.kartven.javaprobackend.http.SingleSlideDetails;
import pl.kartven.javaprobackend.http.mapper.LectureMapper;
import pl.kartven.javaprobackend.http.mapper.SlideMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class LectureService {
    private final LectureRepository lectureRepository;
    private final LectureMapper lectureMapper;
    private final SlideMapper slideMapper;

    public List<SingleLectureDetails> getAllLectures() {
        return Option.of(lectureRepository.findAll())
                .map(lectureMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }

    @Transactional
    public List<SingleSlideDetails> getSlidesByLectureId(Long id) {
        return Option.of(Option
                        .ofOptional(lectureRepository.findById(id))
                        .getOrElseThrow(NotFoundException::new)
                )
                .map(Lecture::getSlides)
                .map(slideMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }
}
