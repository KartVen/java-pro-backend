package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kartven.javaprobackend.infra.model.entity.ExternalLink;
import pl.kartven.javaprobackend.infra.restapi.dto.LinkDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LinkMapper {
    List<LinkDto> map(List<ExternalLink> externalLinks);

    @Mapping(target = "content", source = "URL")
    LinkDto map(ExternalLink externalLink);
}
