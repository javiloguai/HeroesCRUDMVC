package com.w2m.heroestest.converters;

import org.springframework.core.convert.converter.Converter;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility to parse dates to String. They can be used everywhere on the API
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

    private String format;

    private DateTimeFormatter formatter;

    public LocalDateConverter(final String format) {
        this.format = format;
        this.formatter = DateTimeFormatter.ofPattern(this.format);
    }

    /**
     * Convert.
     *
     * @param value the value
     * @return the local date
     */
    @Override
    public LocalDate convert(final String value) {
        LocalDate result = null;
        boolean error = false;
        if (value.isEmpty()) {
            return null;
        }

        try {
            result = LocalDate.parse(value, this.formatter);
        } catch (final DateTimeParseException ex) {
            error = true;
        }

        if (error) {
            throw new DateTimeException(String.format("unable to parse (%s). Supported format is %s", value,
                    String.join(", ", this.format)));
        }

        return result;

    }

}
