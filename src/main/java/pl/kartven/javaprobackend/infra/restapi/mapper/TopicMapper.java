package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kartven.javaprobackend.infra.model.entity.Topic;
import pl.kartven.javaprobackend.infra.restapi.dto.TopicReqDto;
import pl.kartven.javaprobackend.infra.restapi.dto.TopicResDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TopicMapper {

    List<TopicResDto> map(List<Topic> topics);

    TopicResDto map(Topic topic);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Topic map(TopicReqDto topicDto);
}
