package com.w2m.heroestest.config.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.w2m.heroestest.config.exception.BusinessRuleViolatedException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * Defines the type of JsonMapper with default Aplication Date Formats
 */
public class DefaultApplicationJsonMapper implements JacksonMapper {

    private final ObjectMapper getMapper;

    public DefaultApplicationJsonMapper(final ObjectMapper getMapper) {
        this.getMapper = getMapper;
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

            final ObjectMapper mapper = getMapper;
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

        return getMapper.readTree(stringValue);
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
        T result = null;
        try {
            result = getMapper.readValue(json.getBytes(), clazz);

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

        T result = null;
        if (object != null) {
            result = getMapper.convertValue(object, clazz);
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

        final Resource resource = new ClassPathResource(fileName);
        T result = null;
        try {
            final InputStream input = resource.getInputStream();
            if (input != null) {
                result = getMapper.readValue(input, clazz);
            }
        } catch (final IOException ioException) {
            throw new BusinessRuleViolatedException(ioException);
        }

        return result;

    }
}
