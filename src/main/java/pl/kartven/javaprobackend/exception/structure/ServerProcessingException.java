package pl.kartven.javaprobackend.exception.structure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ServerProcessingException extends RuntimeException {
    public ServerProcessingException() {
        super("Failed to processing");
    }

    public ServerProcessingException(String message) {
        super(message);
    }
}
