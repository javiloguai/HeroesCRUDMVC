package com.w2m.heroestest.config.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@ResponseStatus(value = HttpStatus.CONFLICT)
public class BusinessRuleViolatedException extends RuntimeException {

    private Object[] parameters;

    public BusinessRuleViolatedException() {
        super();
    }

    public BusinessRuleViolatedException(String message) {
        super(message);
    }

    public BusinessRuleViolatedException(final Throwable cause) {
        super(cause);
    }

    public BusinessRuleViolatedException(String message, Object[] parameters) {
        super(message);
        this.parameters = parameters;
    }

    public BusinessRuleViolatedException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
