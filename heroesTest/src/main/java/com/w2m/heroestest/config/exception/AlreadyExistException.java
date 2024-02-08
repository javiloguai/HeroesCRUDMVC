package com.w2m.heroestest.config.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Class ServiceException.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
@EqualsAndHashCode(callSuper = true)
@Data
public class AlreadyExistException extends RuntimeException {

    private Object[] parameters;

    public AlreadyExistException() {
        super();
    }

    public AlreadyExistException(final Throwable cause) {
        super(cause);
    }

    public AlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistException(final String message) {
        super(message);
    }

    public AlreadyExistException(String message, Object[] parameters) {
        super(message);
        this.parameters = parameters;
    }

}
