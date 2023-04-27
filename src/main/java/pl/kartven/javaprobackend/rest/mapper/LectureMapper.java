package pl.kartven.javaprobackend.rest.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.lecture.Lecture;
import pl.kartven.javaprobackend.rest.SingleLectureDetails;

import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public abstract class LectureMapper {
    public abstract List<SingleLectureDetails> map(List<Lecture> lectures);
}
