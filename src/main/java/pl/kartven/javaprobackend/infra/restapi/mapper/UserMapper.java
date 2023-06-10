package pl.kartven.javaprobackend.infra.restapi.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.kartven.javaprobackend.infra.model.user.User;
import pl.kartven.javaprobackend.infra.restapi.dto.AuthRequest;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public abstract class UserMapper {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "nickname", source = "nickname", qualifiedByName = "mapNickname"),
            @Mapping(target = "role", expression = "java(pl.kartven.javaprobackend.infra.model.user.UserRole.ROLE_USER)"),
            @Mapping(target = "tokens", ignore = true),
            @Mapping(target = "authorities", ignore = true),
            @Mapping(target = "password", source = "password", qualifiedByName = "mapPassword")
    })
    public abstract User map(AuthRequest.Register body);

    @Named("mapNickname")
    protected String mapNickname(String nickname){
        return nickname.toLowerCase();
    }

    @Named("mapPassword")
    protected String mapPassword(String password){
        return passwordEncoder.encode(password);
    }
}
