package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kartven.javaprobackend.infra.model.entity.Slide;
import pl.kartven.javaprobackend.infra.restapi.dto.SlideResDto;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class SlideMapper {
    public abstract List<SlideResDto> map(List<Slide> slides);

    @Mapping(target = "content", source = "imageBytes")
    public abstract SlideResDto map(Slide slide);
}
