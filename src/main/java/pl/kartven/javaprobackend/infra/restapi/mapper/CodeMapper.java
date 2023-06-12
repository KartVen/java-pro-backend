package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.code.Code;
import pl.kartven.javaprobackend.infra.restapi.dto.CodeDto;

import java.util.List;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public abstract class CodeMapper {
    public abstract List<CodeDto> map(List<Code> codes);
    public abstract CodeDto map(Code code);
}
