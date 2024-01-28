package com.w2m.heroestest.config.jackson;

/**
 * Defines the type of JsonMapper with default Aplication Date Formats
 */
public class DefaultApplicationJsonMapper extends BaseJacksonMapper implements JacksonMapper {

    public DefaultApplicationJsonMapper(final String dateFormat, final String dateTimeFormat) {
        super(dateFormat, dateTimeFormat);
    }

}
