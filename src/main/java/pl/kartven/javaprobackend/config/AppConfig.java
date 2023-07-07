package pl.kartven.javaprobackend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.HashSet;
import java.util.Set;

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

    @Data
    @Configuration
    @ConfigurationProperties("app.content")
    public static class ContentProperties {
        private Set<String> imageWhitelist = new HashSet<>();
        private Float compressScale;
    }
}
