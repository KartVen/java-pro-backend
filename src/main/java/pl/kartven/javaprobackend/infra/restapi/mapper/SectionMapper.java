package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.entity.Section;
import pl.kartven.javaprobackend.infra.restapi.dto.SectionReqDto;
import pl.kartven.javaprobackend.infra.restapi.dto.SectionResDto;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public abstract class SectionMapper {
    public abstract List<SectionResDto> map(List<Section> sections);

    public abstract SectionResDto map(Section section);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "topic", ignore = true)
    @Mapping(target = "user", ignore = true)
    public abstract Section map(SectionReqDto sectionDto);
}