package pl.kartven.javaprobackend.infra.auth;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.kartven.javaprobackend.infra.restapi.UserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipal;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserService userService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(JwtUtil.BEARER_TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = jwtUtil.extractToken(authHeader);
        Option
                .of(token)
                .map(jwtUtil::extractUsername)
                .map(userService::loadUserByUsername)
                .filter(user -> jwtUtil.isValid(token, user))
                .map(user -> new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()))
                .peek(authToken -> {
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                });
        filterChain.doFilter(request, response);
    }


}
