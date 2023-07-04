package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.entity.Code;
import pl.kartven.javaprobackend.infra.restapi.dto.CodeDto;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface CodeMapper {
    List<CodeDto> map(List<Code> codes);

    CodeDto map(Code code);
}
