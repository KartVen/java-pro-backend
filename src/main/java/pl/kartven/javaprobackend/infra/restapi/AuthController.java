package pl.kartven.javaprobackend.infra.restapi;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kartven.javaprobackend.infra.restapi.dto.AuthReqDto;
import pl.kartven.javaprobackend.infra.restapi.dto.AuthResDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/login")
    public ResponseEntity<AuthResDto> login(@Valid @RequestBody AuthReqDto.Login body){
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(body));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResDto> register(@Valid @RequestBody AuthReqDto.Register body){
        return ResponseEntity.status(HttpStatus.OK).body(authService.register(body));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refreshToken(request, response);
    }
}
