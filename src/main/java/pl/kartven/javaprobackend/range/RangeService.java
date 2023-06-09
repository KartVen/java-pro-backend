package pl.kartven.javaprobackend.range;

import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kartven.javaprobackend.exception.NotFoundException;
import pl.kartven.javaprobackend.exception.ServerProcessingException;
import pl.kartven.javaprobackend.restapi.SingleRangeDetails;
import pl.kartven.javaprobackend.restapi.SingleSlideDetails;
import pl.kartven.javaprobackend.restapi.mapper.SlideMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class RangeService {
    private final RangeRepository rangeRepository;
    private final pl.kartven.javaprobackend.restapi.mapper.rangeMapper rangeMapper;
    private final SlideMapper slideMapper;

    public List<SingleRangeDetails> getRangeList() {
        return Option.of(rangeRepository.findAll())
                .map(rangeMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }

    @Transactional
    public List<SingleSlideDetails> getSlidesByRangeId(Long id) {
        return Option.of(Option
                        .ofOptional(rangeRepository.findById(id))
                        .getOrElseThrow(NotFoundException::new)
                )
                .map(Range::getSlides)
                .map(slideMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }
}
