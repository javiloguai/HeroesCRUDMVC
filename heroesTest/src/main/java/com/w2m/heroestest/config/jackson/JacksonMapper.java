package com.w2m.heroestest.config.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * The Interface JacksonMapper. Defines the infrastructure methods to manage the JSON objects
 */
public interface JacksonMapper {

    /**
     * Gets the json from object.
     *
     * @param abstractConfigDto the abstract config dto
     * @return the json from object
     */
    String getJsonFromObject(final Object abstractConfigDto);

    /**
     * Gets the json node from string.
     *
     * @param stringValue the string value
     * @return the json node from string
     */
    JsonNode getJsonNodeFromString(final String stringValue) throws JsonProcessingException;

    /**
     * Gets the object from json.
     *
     * @param <T>   the generic type
     * @param json  the json
     * @param clazz the clazz
     * @return the object from json
     */
    <T> T getObjectFromJson(final String json, final Class<T> clazz);

    <T> T getObjectFromJson(final Object object, final Class<T> clazz);

    /**
     * Gets the object from resource file.
     *
     * @param <T>      the generic type
     * @param fileName the file name
     * @param clazz    the clazz
     * @return the object from resource file
     */
    <T> T getObjectFromResourceFile(final String fileName, final Class<T> clazz);

}
