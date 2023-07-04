package pl.kartven.javaprobackend.exception.structure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailExistException extends ServerProcessingException {
    public EmailExistException() {
        super("This email address is already taken.");
    }
}
