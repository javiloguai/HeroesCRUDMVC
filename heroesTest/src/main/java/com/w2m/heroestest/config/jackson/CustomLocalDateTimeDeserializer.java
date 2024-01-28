package com.w2m.heroestest.config.jackson;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.w2m.heroestest.converters.LocalDateTimeConverter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Json deserializer for timestamps
 */
public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    /**
     * The converter.
     */
    private final LocalDateTimeConverter converter;

    /**
     * Instantiates a new custom local date time deserializer.
     *
     * @param converter the converter
     */
    public CustomLocalDateTimeDeserializer(final LocalDateTimeConverter converter) {
        this.converter = converter;
    }

    /**
     * Deserialize.
     *
     * @param jsonParser the json parser
     * @param ctxt       the ctxt
     * @return the local date time
     *
     * @throws IOException      Signals that an I/O exception has occurred.
     * @throws JacksonException the jackson exception
     */
    @Override
    public LocalDateTime deserialize(final JsonParser jsonParser, final DeserializationContext ctxt)
            throws IOException, JacksonException { // NOSONAR ctxt is not necessary
        return converter.convert(jsonParser.getText());
    }

}
