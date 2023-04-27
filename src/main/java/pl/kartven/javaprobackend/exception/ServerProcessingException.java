package pl.kartven.javaprobackend.exception;

public class ServerProcessingException extends RuntimeException {
    public ServerProcessingException() {
        super("Failed to processing");
    }
}
