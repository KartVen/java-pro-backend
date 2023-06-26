package pl.kartven.javaprobackend.infra.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.kartven.javaprobackend.exception.structure.TokenProcessingException;
import pl.kartven.javaprobackend.infra.auth.JwtUtil;
import pl.kartven.javaprobackend.infra.restapi.dto.AuthRequest;
import pl.kartven.javaprobackend.infra.restapi.dto.AuthResponse;
import pl.kartven.javaprobackend.infra.restapi.mapper.UserMapper;
import pl.kartven.javaprobackend.infra.model.token.Token;
import pl.kartven.javaprobackend.infra.model.token.TokenRepo;
import pl.kartven.javaprobackend.infra.model.user.User;
import pl.kartven.javaprobackend.infra.model.user.UserRepo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final TokenRepo tokenRepo;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;

    public AuthResponse register(AuthRequest.Register body) {
        return Option.of(userMapper.map(body))
                .map(userRepo::save)
                .map(this::generateNewTokensAndGetAuthResponse)
                .getOrElseThrow(() -> new TokenProcessingException("Failed to processing authentication"));
    }

    public AuthResponse login(AuthRequest.Login body) {
        return Option.of(new UsernamePasswordAuthenticationToken(
                                body.getEmail(), body.getPassword()
                        )
                )
                .map(authenticationManager::authenticate)
                .map(authentication -> (User) authentication.getPrincipal())
                .map(user -> userRepo.findByEmail(user.getEmail()))
                .map(Optional::get)
                .map(this::generateNewTokensAndGetAuthResponse)
                .getOrElseThrow(() -> new TokenProcessingException("Failed to processing authentication"));
    }

    private AuthResponse generateNewTokensAndGetAuthResponse(User user, String refreshToken) {
        this.revokeAllUserTokens(user);
        return Option
                .of(new Token(jwtUtil.generateToken(user), user))
                .map(tokenRepo::save)
                .map(token -> new AuthResponse(user.getNickname(), token.getValue(), refreshToken))
                .getOrNull();
    }

    private AuthResponse generateNewTokensAndGetAuthResponse(User user) {
        return this.generateNewTokensAndGetAuthResponse(user, jwtUtil.generateRefreshToken(user));
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepo.findAllValidTokenByUser(user.getId());
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(JwtUtil.BEARER_TOKEN_PREFIX)) return;
        String refreshToken = jwtUtil.extractToken(authHeader);
        var authResponse = Option
                .of(refreshToken)
                .map(jwtUtil::extractUsername)
                .map(userRepo::findByEmail)
                .map(Optional::get)
                .filter(user -> jwtUtil.isValid(refreshToken, user))
                .map(user -> generateNewTokensAndGetAuthResponse(user, refreshToken));
        objectMapper.writeValue(response.getOutputStream(), authResponse);
    }
}
