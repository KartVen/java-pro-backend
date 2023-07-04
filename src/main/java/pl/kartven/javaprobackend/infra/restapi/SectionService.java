package pl.kartven.javaprobackend.infra.restapi;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kartven.javaprobackend.exception.structure.ServerProcessingException;
import pl.kartven.javaprobackend.infra.model.repository.CodeRepository;
import pl.kartven.javaprobackend.infra.model.repository.ExternalLinkRepository;
import pl.kartven.javaprobackend.infra.restapi.dto.CodeDto;
import pl.kartven.javaprobackend.infra.restapi.dto.LinkDto;
import pl.kartven.javaprobackend.infra.restapi.mapper.CodeMapper;
import pl.kartven.javaprobackend.infra.restapi.mapper.LinkMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectionService {
    private final CodeRepository codeRepository;
    private final CodeMapper codeMapper;
    private final ExternalLinkRepository externalLinkRepository;
    private final LinkMapper linkMapper;

    public List<CodeDto> getCodesOfSection(Long id) {
        return Option.of(codeRepository.findByTopic_Id(id))
                .map(codeMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }

    public List<LinkDto> getLinksOfSection(Long id) {
        return Option.of(externalLinkRepository.findByTopic_Id(id))
                .map(linkMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }
}
