package pl.kartven.javaprobackend.infra.restapi;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.exception.structure.NotFoundException;
import pl.kartven.javaprobackend.exception.structure.ServerProcessingException;
import pl.kartven.javaprobackend.infra.model.entity.Slide;
import pl.kartven.javaprobackend.infra.model.entity.Topic;
import pl.kartven.javaprobackend.infra.model.repository.QuizRepository;
import pl.kartven.javaprobackend.infra.model.repository.SectionRepository;
import pl.kartven.javaprobackend.infra.model.repository.SlideRepository;
import pl.kartven.javaprobackend.infra.model.repository.TopicRepository;
import pl.kartven.javaprobackend.infra.restapi.dto.*;
import pl.kartven.javaprobackend.infra.restapi.mapper.QuizMapper;
import pl.kartven.javaprobackend.infra.restapi.mapper.SectionMapper;
import pl.kartven.javaprobackend.infra.restapi.mapper.SlideMapper;
import pl.kartven.javaprobackend.infra.restapi.mapper.TopicMapper;
import pl.kartven.javaprobackend.utility.ImageUtils;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static pl.kartven.javaprobackend.config.AppConfig.ContentProperties;

@Service
@RequiredArgsConstructor
public class TopicService {
    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;
    private final SectionMapper sectionMapper;
    private final QuizMapper quizMapper;
    private final SectionRepository sectionRepository;
    private final QuizRepository quizRepository;
    private final ContentProperties contentProperties;
    private final SlideRepository slideRepository;
    private final SlideMapper slideMapper;
    private final UserService userService;

    public List<TopicResDto> getTopics(Long id) {
        return getTopicsFromDB(() ->
                (Objects.isNull(id)) ? topicRepository.findAll() : topicRepository.findByUser_Id(id)
        );
    }

    private List<TopicResDto> getTopicsFromDB(Supplier<List<Topic>> supplier) {
        return Option.of(supplier.get())
                .map(topicMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }

    public List<SectionResDto> getSectionsOfTopic(Long id) {
        return Option.of(sectionRepository.findByTopic_Id(id))
                .map(sectionMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }

    public List<QuizDto> getQuizzesOfTopic(Long id) {
        return Option.of(quizRepository.findByTopic_Id(id))
                .map(quizMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }

    public void postSlidesOfTopic(Long id, SlidesReqDto body) {
        Option.of(body)
                .map(SlidesReqDto::getSlides)
                .peek(slides -> slides.forEach(slide -> processSlide(id, slide)));
    }

    private void processSlide(Long id, MultipartFile slide) {
        if (slide.isEmpty()) throw new ServerProcessingException();
        String filename = Option.of(slide.getOriginalFilename()).getOrElse("");
        Option.of(slide)
                .filter(v -> isImageFile(filename))
                .onEmpty(() -> {
                    throw new ServerProcessingException();
                })
                .map(multipartFile -> ImageUtils.compressImage(multipartFile, contentProperties.getCompressScale()))
                .map(bytes -> new Slide(filename, bytes, topicRepository.findById(id).orElseThrow(NotFoundException::new)))
                .peek(slideRepository::save);
    }

    private boolean isImageFile(String fileName) {
        return !Option.of(ImageUtils.getFileExtension(fileName))
                .filter(v -> contentProperties.getImageWhitelist().contains(v))
                .isEmpty();
    }

    public List<SlideResDto> getSlidesOfTopic(Long id, Integer page, Integer size) {
        Pageable pageable = (Objects.isNull(page) || Objects.isNull(size)) ?
                Pageable.unpaged() : PageRequest.of(page, size);
        return Option.of(slideRepository.findByTopic_Id(id, pageable))
                .map(slideMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }

    public void postTopic(TopicReqDto body) {
        Option.of(body)
                .map(topicMapper::map)
                .peek(topic -> topic.setUser(userService.getContenxtUser()))
                .map(topicRepository::save)
                .onEmpty(() -> {
                    throw new ServerProcessingException();
                });
    }

    public void postSectionOfTopic(Long id, SectionReqDto body) {
        Option.of(body)
                .map(sectionMapper::map)
                .peek(section -> {
                    section.setTopic(topicRepository.findById(id).orElseThrow(NotFoundException::new));
                    section.setUser(userService.getContenxtUser());
                })
                .map(sectionRepository::save)
                .onEmpty(() -> {
                    throw new ServerProcessingException();
                });
    }
}
