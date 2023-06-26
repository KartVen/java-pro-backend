package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.exception.structure.NotFoundException;
import pl.kartven.javaprobackend.infra.model.topic.Topic;
import pl.kartven.javaprobackend.infra.model.topic.TopicRepository;
import pl.kartven.javaprobackend.infra.restapi.dto.TopicDto;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class TopicMapper {
    @Autowired
    private TopicRepository topicRepository;

    public abstract List<TopicDto> map(List<Topic> topics);
    public abstract TopicDto map(Topic topic);

    protected Topic map(Long id){
        return topicRepository.findById(id).orElseThrow(NotFoundException::new);
    }
}
