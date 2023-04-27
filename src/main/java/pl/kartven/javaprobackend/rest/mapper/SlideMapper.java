package pl.kartven.javaprobackend.rest.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.lecture.Lecture;
import pl.kartven.javaprobackend.lecture.slide.Slide;
import pl.kartven.javaprobackend.rest.SingleLectureDetails;
import pl.kartven.javaprobackend.rest.SingleSlideDetails;

import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public abstract class SlideMapper {
    public abstract List<SingleSlideDetails> map(List<Slide> slides);
}
