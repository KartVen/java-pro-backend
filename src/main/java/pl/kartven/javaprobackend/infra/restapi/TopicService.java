package pl.kartven.javaprobackend.infra.restapi;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.exception.structure.NotFoundException;
import pl.kartven.javaprobackend.exception.structure.ServerProcessingException;
import pl.kartven.javaprobackend.infra.model.quiz.QuizRepository;
import pl.kartven.javaprobackend.infra.model.section.SectionRepository;
import pl.kartven.javaprobackend.infra.model.slide.Slide;
import pl.kartven.javaprobackend.infra.model.slide.SlideRepository;
import pl.kartven.javaprobackend.infra.model.topic.Topic;
import pl.kartven.javaprobackend.infra.model.topic.TopicRepository;
import pl.kartven.javaprobackend.infra.restapi.dto.*;
import pl.kartven.javaprobackend.infra.restapi.mapper.QuizMapper;
import pl.kartven.javaprobackend.infra.restapi.mapper.SectionMapper;
import pl.kartven.javaprobackend.infra.restapi.mapper.SlideMapper;
import pl.kartven.javaprobackend.infra.restapi.mapper.TopicMapper;
import pl.kartven.javaprobackend.utility.ImageUtils;

import javax.validation.Valid;
import java.util.List;

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

    public List<TopicDto> getTopics() {
        return Option.of(topicRepository.findAll())
                .map(topicMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }


    public List<SectionDto> getSectionsOfTopic(Long id) {
        return Option.of(sectionRepository.findByTopic_Id(id))
                .map(sectionMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }

    public List<QuizDto> getQuizzesOfTopic(Long id) {
        return Option.of(quizRepository.findByTopic_Id(id))
                .map(quizMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }

    public void postSlidesOfTopic(Long id, @Valid SlidesReqDto body) {
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
                .map(bytes -> new Slide(filename, bytes, findTopic(id)))
                .peek(slideRepository::save);
    }

    private Topic findTopic(Long id) {
        return topicRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    private boolean isImageFile(String fileName) {
        return !Option.of(ImageUtils.getFileExtension(fileName))
                .filter(v -> contentProperties.getImageWhitelist().contains(v))
                .isEmpty();
    }

    public List<SlideResDto> getSlidesOfTopic(Long id) {
        return Option.of(slideRepository.findByTopic_Id(id))
                .map(slideMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
    }
}
