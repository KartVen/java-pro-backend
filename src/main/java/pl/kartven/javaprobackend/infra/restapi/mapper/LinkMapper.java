package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kartven.javaprobackend.infra.model.externallink.ExternalLink;
import pl.kartven.javaprobackend.infra.restapi.dto.LinkDto;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LinkMapper {
    public abstract List<LinkDto> map(List<ExternalLink> externalLinks);

    @Mapping(target = "content", source = "URL")
    public abstract LinkDto map(ExternalLink externalLink);
}
