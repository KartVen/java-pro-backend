package pl.kartven.javaprobackend.infra.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ROLE_USER,
    ROLE_MODERATOR,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
