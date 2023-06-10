package pl.kartven.javaprobackend.infra.auth;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.kartven.javaprobackend.infra.model.token.TokenRepo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutHandler {

    private final TokenRepo tokenRepo;
    private final JwtUtil jwtUtil;

    @Override
    public void logout(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(JwtUtil.BEARER_TOKEN_PREFIX)) return;
        Option
                .of(jwtUtil.extractToken(authHeader))
                .map(tokenRepo::findByValue)
                .map(Optional::get)
                .map(token -> {
                    token.setExpired(true);
                    token.setRevoked(true);
                    return token;
                })
                .map(tokenRepo::save)
                .map(token -> {
                    SecurityContextHolder.clearContext();
                    return true;
                });
    }
}
