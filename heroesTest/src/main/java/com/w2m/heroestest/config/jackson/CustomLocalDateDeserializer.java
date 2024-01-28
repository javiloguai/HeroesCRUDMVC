package com.w2m.heroestest.config.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.w2m.heroestest.converters.LocalDateConverter;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Json deserializer for dates
 */
public class CustomLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    /**
     * The converter.
     */
    private final LocalDateConverter converter;

    /**
     * Instantiates a new custom local date deserializer.
     *
     * @param converter the converter
     */
    public CustomLocalDateDeserializer(final LocalDateConverter converter) {
        this.converter = converter;
    }

    /**
     * Deserialize.
     *
     * @param jsonParser the json parser
     * @param ctxt       the ctxt
     * @return the local date
     *
     * @throws IOException      Signals that an I/O exception has occurred.
     * @throws JacksonException the jackson exception
     */
    @Override
    public LocalDate deserialize(final JsonParser jsonParser, final DeserializationContext ctxt)
            throws IOException, JacksonException { // NOSONAR ctxt is not necessary
        return converter.convert(jsonParser.getText());
    }

}
