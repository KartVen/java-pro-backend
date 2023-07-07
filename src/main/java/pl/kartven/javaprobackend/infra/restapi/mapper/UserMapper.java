package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.entity.User;
import pl.kartven.javaprobackend.infra.model.entity.UserRole;
import pl.kartven.javaprobackend.infra.restapi.dto.AuthReqDto;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "nickname", source = "nickname", qualifiedByName = "mapNickname"),
            @Mapping(target = "role", source = "body", qualifiedByName = "mapRole"),
            @Mapping(target = "tokens", ignore = true),
            @Mapping(target = "authorities", ignore = true),
            @Mapping(target = "password", ignore = true)
    })
    User map(AuthReqDto.Register body);

    @Named("mapNickname")
    default String mapNickname(String nickname) {
        return nickname.toLowerCase();
    }

    @Named("mapRole")
    default UserRole mapUser(AuthReqDto.Register body){
        return UserRole.ROLE_USER;
    }
}
