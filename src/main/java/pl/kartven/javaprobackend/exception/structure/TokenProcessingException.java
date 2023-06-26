package pl.kartven.javaprobackend.exception.structure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TokenProcessingException extends ServerProcessingException {
    public TokenProcessingException(String message) {
        super(message);
    }
}
