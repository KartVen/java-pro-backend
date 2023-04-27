package pl.kartven.javaprobackend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties
@Import(value = {
        SecurityConfig.class
})
public class AppConfig {
    @Data
    @Configuration
    @ConfigurationProperties("app.security.role")
    public static class RoleProperties {
        private String defaultRole;
    }
}
