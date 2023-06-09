package pl.kartven.javaprobackend.restapi.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.range.Range;
import pl.kartven.javaprobackend.restapi.SingleRangeDetails;

import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public abstract class rangeMapper {
    public abstract List<SingleRangeDetails> map(List<Range> ranges);
}
