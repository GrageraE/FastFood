package es.grupoO.FastFood.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadAssignmentException extends RuntimeException {
    public BadAssignmentException(String message) {
        super(message);
    }
}
