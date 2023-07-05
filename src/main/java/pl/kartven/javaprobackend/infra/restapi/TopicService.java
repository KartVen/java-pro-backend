package pl.kartven.javaprobackend.infra.restapi;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.kartven.javaprobackend.exception.structure.NotFoundException;
import pl.kartven.javaprobackend.exception.structure.ServerProcessingException;
import pl.kartven.javaprobackend.infra.model.entity.*;
import pl.kartven.javaprobackend.infra.model.repository.*;
import pl.kartven.javaprobackend.infra.restapi.dto.*;
import pl.kartven.javaprobackend.infra.restapi.mapper.*;
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
    private final CodeMapper codeMapper;
    private final CodeRepository codeRepository;
    private final LinkMapper linkMapper;
    private final ExternalLinkRepository externalLinkRepository;

    public List<TopicResDto> getTopics(Long userId) {
        return getTopicsFromDB(() ->
                (Objects.isNull(userId)) ? topicRepository.findAll() : topicRepository.findByUser_Id(userId)
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

    public List<QuizResDto> getQuizzesOfTopic(Long id, Long userId) {
        return getQuizzesOfTopicFromDB(() ->
                (Objects.isNull(userId)) ? quizRepository.findByTopic_Id(id) : quizRepository.findByTopic_IdAndUser_Id(id, userId));
    }

    private List<QuizResDto> getQuizzesOfTopicFromDB(Supplier<List<Quiz>> supplier) {
        return Option.of(supplier.get())
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

    public TopicResDto postTopic(TopicReqDto body) {
        return Option.of(body)
                .map(topicMapper::map)
                .peek(topic -> topic.setUser(userService.getContenxtUser()))
                .map(topicRepository::save)
                .map(topicMapper::map)
                .getOrElseThrow(ServerProcessingException::new);
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

    public void postCodesOfTopic(Long id, CodeReqDto body) {
        Option.of(body)
                .map(codeMapper::map)
                .peek(code -> {
                    code.setTopic(topicRepository.findById(id).orElseThrow(NotFoundException::new));
                    code.setUser(userService.getContenxtUser());
                    code.setSection(proccessAndGetSection(
                            code.getSection(),
                            userService.getContenxtUser(),
                            code.getTopic()
                    ));
                })
                .map(codeRepository::save)
                .onEmpty(() -> {
                    throw new ServerProcessingException();
                });
    }

    private Section proccessAndGetSection(Section section, User user, Topic topic) {
        return Option.of(section.getId())
                .map(secId -> sectionRepository.findById(secId).orElse(null))
                .getOrElse(() -> {
                    section.setTopic(topic);
                    section.setUser(user);
                    return section;
                });
    }

    public void postLinkOfTopic(Long id, LinkReqDto body) {
        Option.of(body)
                .map(linkMapper::map)
                .peek(link -> {
                    link.setTopic(topicRepository.findById(id).orElseThrow(NotFoundException::new));
                    link.setUser(userService.getContenxtUser());
                    link.setSection(proccessAndGetSection(
                            link.getSection(),
                            link.getUser(),
                            link.getTopic()
                    ));
                })
                .map(externalLinkRepository::save)
                .onEmpty(() -> {
                    throw new ServerProcessingException();
                });
    }

    public void postQuizOfTopic(Long id, QuizReqDto body) {
        Option.of(body)
                .map(quizMapper::map)
                .peek(quiz -> {
                    quiz.setTopic(topicRepository.findById(id).orElseThrow(NotFoundException::new));
                    quiz.setUser(userService.getContenxtUser());
                    quiz.getQuestions().forEach(question -> {
                        question.setQuiz(quiz);
                        question.getAnswers().forEach(answer -> answer.setQuestion(question));
                    });
                })
                .map(quizRepository::save)
                .onEmpty(() -> {
                    throw new ServerProcessingException();
                });
    }
}
