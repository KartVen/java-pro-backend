package pl.kartven.javaprobackend.infra.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.kartven.javaprobackend.exception.structure.EmailExistException;
import pl.kartven.javaprobackend.exception.structure.TokenProcessingException;
import pl.kartven.javaprobackend.infra.auth.JwtUtil;
import pl.kartven.javaprobackend.infra.model.entity.Token;
import pl.kartven.javaprobackend.infra.model.entity.User;
import pl.kartven.javaprobackend.infra.model.repository.TokenRepository;
import pl.kartven.javaprobackend.infra.model.repository.UserRepository;
import pl.kartven.javaprobackend.infra.restapi.dto.AuthReqDto;
import pl.kartven.javaprobackend.infra.restapi.dto.AuthResDto;
import pl.kartven.javaprobackend.infra.restapi.mapper.UserMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final TokenRepository tokenRepo;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public AuthResDto register(AuthReqDto.Register body) {
        return Option.of(userMapper.map(body))
                .peek(user -> user.setPassword(passwordEncoder.encode(body.getPassword())))
                .peek(this::validateExistEmail)
                .map(userRepo::save)
                .map(this::generateNewTokensAndResponse)
                .getOrElseThrow(() -> new TokenProcessingException("Failed to processing authentication"));
    }

    private void validateExistEmail(User user) {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) throw new EmailExistException();
    }

    public AuthResDto login(AuthReqDto.Login body) {
        return Option.of(new UsernamePasswordAuthenticationToken(
                                body.getEmail(), body.getPassword()
                        )
                )
                .map(authenticationManager::authenticate)
                .map(authentication -> (User) authentication.getPrincipal())
                .map(user -> userRepo.findByEmail(user.getEmail()))
                .map(Optional::get)
                .map(this::generateNewTokensAndResponse)
                .getOrElseThrow(() -> new TokenProcessingException("Failed to processing authentication"));
    }

    private AuthResDto generateNewTokensAndResponse(User user, String refreshToken) {
        this.revokeAllUserTokens(user);
        return Option
                .of(new Token(jwtUtil.generateToken(user), user))
                .map(tokenRepo::save)
                .map(token -> new AuthResDto(
                        user.getId(),
                        user.getNickname(),
                        user.getEmail(),
                        token.getValue(),
                        refreshToken
                ))
                .getOrNull();
    }

    private AuthResDto generateNewTokensAndResponse(User user) {
        return this.generateNewTokensAndResponse(user, jwtUtil.generateRefreshToken(user));
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
                .map(user -> generateNewTokensAndResponse(user, refreshToken));
        objectMapper.writeValue(response.getOutputStream(), authResponse);
    }
}
