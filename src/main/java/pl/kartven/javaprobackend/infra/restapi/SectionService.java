package pl.kartven.javaprobackend.infra.restapi;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kartven.javaprobackend.exception.ServerProcessingException;
import pl.kartven.javaprobackend.infra.model.code.CodeRepository;
import pl.kartven.javaprobackend.infra.restapi.dto.CodeDto;
import pl.kartven.javaprobackend.infra.restapi.mapper.CodeMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final CodeRepository codeRepository;
    private final CodeMapper codeMapper;

    public List<CodeDto> getCodesOfSection(Long id) {
        return Option.of(codeRepository.findByTopic_Id(id))
                .map(codeMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }
}
