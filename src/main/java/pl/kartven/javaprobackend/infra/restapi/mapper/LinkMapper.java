package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.kartven.javaprobackend.infra.model.entity.ExternalLink;
import pl.kartven.javaprobackend.infra.model.entity.Section;
import pl.kartven.javaprobackend.infra.restapi.dto.LinkReqDto;
import pl.kartven.javaprobackend.infra.restapi.dto.LinkResDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LinkMapper {
    List<LinkResDto> map(List<ExternalLink> externalLinks);

    @Mapping(target = "content", source = "url")
    LinkResDto map(ExternalLink externalLink);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "topic", ignore = true)
    @Mapping(target = "section", source = "section", qualifiedByName = "mapSection")
    @Mapping(target = "user", ignore = true)
    ExternalLink map(LinkReqDto linkReqDto);

    @Named("mapSection")
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "topic", ignore = true)
    Section mapSection(LinkReqDto.SectionDto sectionDto);
}
