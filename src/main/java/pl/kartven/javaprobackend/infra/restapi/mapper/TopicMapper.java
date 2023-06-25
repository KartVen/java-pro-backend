package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.topic.Topic;
import pl.kartven.javaprobackend.infra.restapi.dto.TopicDto;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public abstract class TopicMapper {
    public abstract List<TopicDto> map(List<Topic> topics);
    public abstract TopicDto map(Topic topic);
}
