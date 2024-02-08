package com.w2m.heroestest.restapi.server.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.w2m.heroestest.config.exception.AlreadyExistException;
import com.w2m.heroestest.config.exception.BusinessRuleViolatedException;
import com.w2m.heroestest.config.exception.NotFoundException;
import com.w2m.heroestest.config.exception.UnauthorizedException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jruizh
 */
@Data
public class BaseController {

    public static final String MESSAGE = "message";

    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    public static final String NOT_FOUND = "NOT_FOUND";

    public static final String UNAUTHORIZED = "UNAUTHORIZED";

    public static final String FORBIDDEN = "FORBIDDEN";

    public static final String CONFLICT = "CONFLICT";

    public static final String DEFAULT_500 = "default.500.exception";

    public static final String DOUBLE_DOT = ": ";

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(SQLException exception) {
        Map<String, Object> item = new HashMap<>();
        item.put(MESSAGE, messageSource.getMessage("default.sql.exception", null, INTERNAL_SERVER_ERROR,
                LocaleContextHolder.getLocale()));
        item.put("exception", exception);
        logger.error(INTERNAL_SERVER_ERROR + DOUBLE_DOT, exception);
        return item;
    }

    /**
     * Handler para Not Found
     *
     * @param exception de no encontrado
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> handleException(NotFoundException exception) {
        Map<String, Object> item = new HashMap<>();
        item.put(MESSAGE, messageSource.getMessage("default.not.found.exception",
                //Añadimos la posibilidad de manejar parámetros para pasar a los mensajes
                exception.getParameters(), NOT_FOUND, LocaleContextHolder.getLocale()));
        logger.error(NOT_FOUND + DOUBLE_DOT, exception);
        return item;
    }

    /**
     * Handler
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, Object> handleException(UnauthorizedException exception) {
        Map<String, Object> item = new HashMap<>();
        item.put(MESSAGE, messageSource.getMessage("default.unauthorized.exception", null, UNAUTHORIZED,
                LocaleContextHolder.getLocale()));
        logger.error(UNAUTHORIZED + DOUBLE_DOT, exception);
        return item;
    }

    /**
     * Handler para Regla de negocio violada
     *
     * @param exception de regla de negocio violada
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public Map<String, Object> handleException(BusinessRuleViolatedException exception) {
        Map<String, Object> item = new HashMap<>();
        item.put(MESSAGE, messageSource.getMessage("default.businessrule.exception", null, exception.getMessage(),
                LocaleContextHolder.getLocale()));
        item.put("adicional", exception.getParameters());
        return item;
    }

    /**
     * Handler
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String, Object> handleException(AccessDeniedException exception) {
        Map<String, Object> item = new HashMap<>();
        String mensaje = exception.getLocalizedMessage();
        if (StringUtils.hasText(mensaje)) {
            mensaje = messageSource.getMessage("default.denied.exception", null, FORBIDDEN,
                    LocaleContextHolder.getLocale());
        }
        item.put(MESSAGE, mensaje);
        logger.error(FORBIDDEN + DOUBLE_DOT, exception);
        return item;
    }

    /**
     * Handler
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(Exception exception) {
        Map<String, Object> item = new HashMap<>();
        item.put(MESSAGE,
                messageSource.getMessage(DEFAULT_500, null, INTERNAL_SERVER_ERROR, LocaleContextHolder.getLocale()));
        logger.error(INTERNAL_SERVER_ERROR + DOUBLE_DOT, exception);
        return item;
    }

    /**
     * Tratamiento para org.springframework.orm.jpa.JpaSystemException
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleException(org.springframework.orm.jpa.JpaSystemException exception) {
        Map<String, Object> item = new HashMap<>();
        String message = exception.getMostSpecificCause().getMessage();
        item.put(MESSAGE, message);
        logger.error(INTERNAL_SERVER_ERROR + DOUBLE_DOT, exception);
        return item;
    }

    /**
     * Tratamiento para JsonProcessingException
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleException(JsonProcessingException exception) {
        Map<String, Object> item = new HashMap<>();
        String message = exception.getMessage();
        item.put(MESSAGE, message);
        logger.error("INTERNAL_SERVER_ERROR: JSON_PROCESSING EXCEPTION", exception);
        return item;
    }

    /**
     * Tratamiento para JsonProcessingException
     *
     * @param exception
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> handleException(AlreadyExistException exception) {
        Map<String, Object> item = new HashMap<>();
        String mensaje = exception.getLocalizedMessage();
        if (StringUtils.hasText(mensaje)) {
            mensaje = messageSource.getMessage("default.alreadyexists.exception", null, CONFLICT,
                    LocaleContextHolder.getLocale());
        }
        item.put(MESSAGE, mensaje);
        logger.error(CONFLICT + DOUBLE_DOT, exception);
        return item;
    }

}
