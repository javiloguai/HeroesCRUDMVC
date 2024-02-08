package com.w2m.heroestest.config.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    private Object[] parameters;

    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message, Object[] parameters) {
        super(message);
        this.parameters = parameters;
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(final Throwable cause) {
        super(cause);
    }

    public UnauthorizedException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
