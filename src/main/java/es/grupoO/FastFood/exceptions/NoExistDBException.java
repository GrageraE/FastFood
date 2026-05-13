package es.grupoO.FastFood.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoExistDBException extends RuntimeException {

    public NoExistDBException(String message) {
        super(message);
    }
}
