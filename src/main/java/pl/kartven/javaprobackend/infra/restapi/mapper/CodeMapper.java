package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.entity.Code;
import pl.kartven.javaprobackend.infra.model.entity.Section;
import pl.kartven.javaprobackend.infra.restapi.dto.CodeReqDto;
import pl.kartven.javaprobackend.infra.restapi.dto.CodeResDto;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface CodeMapper {
    List<CodeResDto> map(List<Code> codes);

    CodeResDto map(Code code);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "topic", ignore = true)
    @Mapping(target = "section", source = "section", qualifiedByName = "mapSection")
    @Mapping(target = "user", ignore = true)
    Code map(CodeReqDto codeReqDto);

    @Named("mapSection")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "topic", ignore = true)
    Section mapSection(CodeReqDto.SectionDto sectionDto);
}
