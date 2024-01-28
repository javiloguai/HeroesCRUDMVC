package com.w2m.heroestest.config.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.w2m.heroestest.config.exception.BusinessRuleViolatedException;
import com.w2m.heroestest.converters.LocalDateConverter;
import com.w2m.heroestest.converters.LocalDateTimeConverter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Implements the methods defined in {@JacksonMapper}
 */
public class BaseJacksonMapper implements JacksonMapper {

    protected static final String JACKSON_TIME_MODULE = "jackson-LocalDateTime";

    /**
     * The serialize date format.
     */

    private String dateFormat;

    private String dateTimeFormat;

    public BaseJacksonMapper(final String dateFormat, final String dateTimeFormat) {
        this.dateFormat = dateFormat;
        this.dateTimeFormat = dateTimeFormat;
    }

    /**
     * Gets the json from object.
     *
     * @param abstractConfigDto the abstract config dto
     * @return the json from object
     */
    @Override
    public String getJsonFromObject(final Object abstractConfigDto) {
        final StringBuilder jsonConfig = new StringBuilder();

        try {
            final ObjectMapper mapper = getJacksonMapper(/*dateFormat, dateTimeFormat*/);
            mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
            final ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
            jsonConfig.append(writer.writeValueAsString(abstractConfigDto));

        } catch (final IOException exception) {
            throw new BusinessRuleViolatedException(exception);
        }

        return jsonConfig.toString();
    }

    /**
     * Gets the json node from string.
     *
     * @param stringValue the string value
     * @return the json node from string
     */
    @Override
    public JsonNode getJsonNodeFromString(final String stringValue) throws JsonProcessingException {

        final ObjectMapper mapper = getMapper();
        return mapper.readTree(stringValue);
    }

    /**
     * Gets the object from json.
     *
     * @param <T>   the generic type
     * @param json  the json
     * @param clazz the clazz
     * @return the object from json
     */
    @Override
    public <T> T getObjectFromJson(final String json, final Class<T> clazz) {
        final ObjectMapper mapper = getMapper();
        T result = null;
        try {
            result = mapper.readValue(json.getBytes(), clazz);

        } catch (final IOException exception) {
            throw new BusinessRuleViolatedException(exception);
        }
        return result;
    }

    /**
     * Gets the object from json.
     *
     * @param <T>    the generic type
     * @param object the object
     * @param clazz  the clazz
     * @return the object from json
     */
    @Override
    public <T> T getObjectFromJson(final Object object, final Class<T> clazz) {
        final ObjectMapper mapper = getMapper();
        T result = null;
        if (object != null) {
            result = mapper.convertValue(object, clazz);
        }

        return result;
    }

    /**
     * Gets the object from resource file.
     *
     * @param <T>      the generic type
     * @param fileName the file name
     * @param clazz    the clazz
     * @return the object from resource file
     */
    @Override
    public <T> T getObjectFromResourceFile(final String fileName, final Class<T> clazz) {
        final ObjectMapper mapper = getMapper();
        final Resource resource = new ClassPathResource(fileName);
        T result = null;
        try {
            final InputStream input = resource.getInputStream();
            if (input != null) {
                result = mapper.readValue(input, clazz);
            }
        } catch (final IOException ioException) {
            throw new BusinessRuleViolatedException(ioException);
        }

        return result;

    }

    /**
     * Gets the mapper.
     *
     * @return the mapper
     */
    @Override
    public ObjectMapper getMapper() {
        return this.getJacksonMapper(/*this.dateFormat, this.dateTimeFormat*/);
    }

    /**
     * Gets the mapper. @return the mapper
     */
    public ObjectMapper getJacksonMapper(/*final String defaultDateFormat, final String defaultDateTimeFormat*/) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());

        final SimpleModule module = new SimpleModule(JACKSON_TIME_MODULE, Version.unknownVersion());

        module.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)));
        module.addDeserializer(LocalDate.class, new CustomLocalDateDeserializer(new LocalDateConverter(dateFormat)));

        module.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        module.addDeserializer(LocalDateTime.class,
                new CustomLocalDateTimeDeserializer(new LocalDateTimeConverter(dateTimeFormat)));
        mapper.registerModule(module);

        return mapper;
    }

    /**
     * Gets the date Format
     *
     * @return the dateFormat
     */
    public String getDateFormat() {
        return dateFormat;
    }

    /**
     * Gets the date Time Format
     *
     * @return the dateTimeFormat
     */
    public String getDateTimeFormat() {
        return dateTimeFormat;
    }
}
