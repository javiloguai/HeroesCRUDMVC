package com.w2m.heroestest.core.config.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 7803512011025213964L;

    private Map criteria;

    private Object[] parameters;

    public NotFoundException() {
        super();
    }

    public NotFoundException(final Throwable cause) {
        super(cause);
    }

    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotFoundException(String message, Object[] parameters) {
        super(message);
        this.parameters = parameters;
    }

    public NotFoundException(String message) {
        super(message);
    }
}
