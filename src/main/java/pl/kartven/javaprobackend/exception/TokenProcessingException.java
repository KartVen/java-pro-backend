package pl.kartven.javaprobackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TokenProcessingException extends RuntimeException {
    public TokenProcessingException(String message) {
        super(message);
    }
}
