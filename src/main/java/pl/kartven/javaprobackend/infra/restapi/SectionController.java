package pl.kartven.javaprobackend.infra.restapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kartven.javaprobackend.infra.restapi.dto.CodeResDto;
import pl.kartven.javaprobackend.infra.restapi.dto.LinkResDto;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @GetMapping("/{id}/codes")
    public ResponseEntity<List<CodeResDto>> getCodesOfSection(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(sectionService.getCodesOfSection(id));
    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkResDto>> getLinksOfSection(
            @PathVariable Long id
    ){
        return ResponseEntity.status(HttpStatus.OK).body(sectionService.getLinksOfSection(id));
    }
}
