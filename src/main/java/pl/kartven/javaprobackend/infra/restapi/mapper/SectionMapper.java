package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.section.Section;
import pl.kartven.javaprobackend.infra.restapi.dto.SectionDto;

import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public abstract class SectionMapper {
    public abstract List<SectionDto> map(List<Section> sections);
    public abstract SectionDto map(Section section);
}