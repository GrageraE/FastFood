package es.grupoO.FastFood.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyAssignedException extends RuntimeException {

    public AlreadyAssignedException(String message) {
        super(message);
    }
}
