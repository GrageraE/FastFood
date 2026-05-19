package es.grupoO.FastFood.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class NotEqualCurrencyException extends RuntimeException {
    public NotEqualCurrencyException(String message) {
        super(message);
    }
}
