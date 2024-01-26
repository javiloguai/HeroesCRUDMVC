package com.w2m.heroestest.core.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * The Class ParamUtils.
 */
public final class ParamUtils {

    /**
     * Constructor to avoid instantiation of the class
     */
    private ParamUtils() {
    }

    /**
     * Param informed.
     *
     * @param field the field
     * @return true, if successful
     */
    public static boolean paramInformed(final String field) {
        return StringUtils.hasText(field);
    }

    /**
     * Param not informed.
     *
     * @param field the field
     * @return true, if successful
     */
    public static boolean paramNotInformed(final String field) {
        return !StringUtils.hasText(field);
    }

    /**
     * Param informed.
     *
     * @param collection the collection
     * @return true, if successful
     */
    public static boolean paramInformed(final Collection<?> collection) {
        return !CollectionUtils.isEmpty(collection);
    }

    /**
     * Param not informed.
     *
     * @param collection the collection
     * @return true, if successful
     */
    public static boolean paramNotInformed(final Collection<?> collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * Param informed.
     *
     * @param map the map
     * @return true, if successful
     */
    public static boolean paramInformed(final Map<?, ?> map) {
        return !CollectionUtils.isEmpty(map);
    }

    /**
     * Param not informed.
     *
     * @param map the map
     * @return true, if successful
     */
    public static boolean paramNotInformed(final Map<?, ?> map) {
        return CollectionUtils.isEmpty(map);
    }

    /**
     * Gets the if is present.
     *
     * @param value the value
     * @return the if is present
     */
    public static String getIfIsPresent(final Optional<String> value) {
        String strValue = null;
        if (value.isPresent()) {
            strValue = value.get();
        }

        return strValue;
    }

    /**
     * Checks if is instance of the class.
     *
     * @param <T>       the generic type
     * @param object    the object
     * @param className the class name
     * @return true, if is instance of the class
     */
    public static <T> boolean isInstanceOfTheClass(final T object, final String className) {
        return object.getClass().getName().equals(className);
    }

    /**
     * Checks if is not instance of the class.
     *
     * @param <T>       the generic type
     * @param object    the object
     * @param className the class name
     * @return true, if is not instance of the class
     */
    public static <T> boolean isNotInstanceOfTheClass(final T object, final String className) {
        return !object.getClass().getName().equals(className);
    }

}
