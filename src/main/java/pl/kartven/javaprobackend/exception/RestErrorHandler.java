package pl.kartven.javaprobackend.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.kartven.javaprobackend.exception.structure.NotFoundException;
import pl.kartven.javaprobackend.exception.structure.ServerProcessingException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@RestControllerAdvice
public final class RestErrorHandler extends ResponseEntityExceptionHandler {
    @Override
    @NonNull
    public @NotNull ResponseEntity<Object> handleExceptionInternal(
            @NonNull Exception ex, Object body, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request
    ) {
        return super.handleExceptionInternal(ex, body == null ? new RestErrorResponse(status, ex) : body, headers, status, request);
    }

    @ExceptionHandler({
            InsufficientAuthenticationException.class,
            AuthenticationException.class,
            ConstraintViolationException.class,
            ServerProcessingException.class,
            NotFoundException.class
    })
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status;
        RestErrorResponse body = null;

        if (ex instanceof InsufficientAuthenticationException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (ex instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
        } else if (ex instanceof ConstraintViolationException) {
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            body = new RestErrorResponse(status, ((ConstraintViolationException) ex).getConstraintViolations().toString());
        } else if (ex instanceof ServerProcessingException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            body = new RestErrorResponse(status, ex.getMessage());
        } else if (ex instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
            body = new RestErrorResponse(status, ex.getMessage());
        } else {
            throw ex;
        }

        return this.

                handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    @NonNull
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request
    ) {
        var errorList = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList()
                .toString();
        return this.handleExceptionInternal(ex, new RestErrorResponse(status, errorList), new HttpHeaders(), status, request);
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class RestErrorResponse {
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Europe/Warsaw")
        private LocalDateTime timeStamp;
        private int status;
        private String error;
        private String message;

        public RestErrorResponse(int status, String error, String message) {
            this.timeStamp = LocalDateTime.now();
            this.status = status;
            this.error = error;
            this.message = message;
        }

        public RestErrorResponse(HttpStatus status, Exception ex) {
            this(status.value(), status.getReasonPhrase(), ex.getMessage());
        }

        public RestErrorResponse(HttpStatus status, String message) {
            this(status.value(), status.getReasonPhrase(), message);
        }

        public static void writeHttpBodyResponse(HttpServletResponse response, ObjectMapper objectMapper, Exception ex, HttpStatus status) throws IOException {
            response.setStatus(status.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.getWriter().write(objectMapper.writeValueAsString(new RestErrorResponse(status, ex)));
        }
    }

}