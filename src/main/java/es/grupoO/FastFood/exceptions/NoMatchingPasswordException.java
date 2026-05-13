package es.grupoO.FastFood.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NoMatchingPasswordException extends RuntimeException {

    public NoMatchingPasswordException(String message) {
        super(message);
    }
}