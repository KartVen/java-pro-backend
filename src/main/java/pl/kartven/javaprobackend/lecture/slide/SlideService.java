package pl.kartven.javaprobackend.lecture.slide;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.exception.ServerProcessingException;
import pl.kartven.javaprobackend.http.mapper.SlideMapper;

import java.io.IOException;

@Service
@AllArgsConstructor
public class SlideService {

    private final SlideRepository slideRepository;
    private final SlideMapper slideMapper;

    public Slide uploadImage(MultipartFile file) {
        return Option.of(file)
                .map(f -> {
                    try {
                        return f.getBytes();
                    } catch (IOException e) {
                        return null;
                    }
                })
                .map(Slide::new)
                .map(slideRepository::save)
                .getOrElseThrow(ServerProcessingException::new);
    }
}
