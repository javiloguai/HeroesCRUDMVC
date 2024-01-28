package com.w2m.heroestest.config.jackson;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.w2m.heroestest.converters.LocalDateConverter;
import com.w2m.heroestest.converters.LocalDateTimeConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class configures the beans and mappers for default custom Jackson Conversion for JSON
 */
@Configuration
public class JacksonConfig {
    protected static final String JACKSON_TIME_MODULE = "jackson-LocalDateTime";


    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    /**
     * Gets the mapper.
     *
     * @return the mapper
     */
    @Bean
    @Primary
    public ObjectMapper getMapper() {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new JavaTimeModule());

        final SimpleModule module = new SimpleModule(JACKSON_TIME_MODULE, Version.unknownVersion());
        module.addSerializer(LocalDate.class,
                new LocalDateSerializer(dateFormatter));
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));

        module.addSerializer(LocalDateTime.class,
                new LocalDateTimeSerializer(dateTimeFormatter));
        module.addDeserializer(LocalDateTime.class,
                new LocalDateTimeDeserializer(dateTimeFormatter));
        mapper.registerModule(module);

        return mapper;
    }


    @Bean
    public LocalDateConverter defaultLocalDateConverter() {
        return new LocalDateConverter("dd-MM-yyyy");
    }


    @Bean
    public LocalDateTimeConverter multiFormatDateTimeConverter() {
        return new LocalDateTimeConverter("dd-MM-yyyy HH:mm:ss");
    }

    /**
     * The DefaultApplicationJsonMapper
     *
     * @return the DefaultApplicationJsonMapper
     */
    @Bean
    public DefaultApplicationJsonMapper defaultApplicationJsonMapper() {
        return new DefaultApplicationJsonMapper("dd-MM-yyyy",
                "dd-MM-yyyy HH:mm:ss");
    }


    /**
     * Support for Java date and time API.
     *
     * @return the corresponding Jackson module.
     */
    @Bean
    public JavaTimeModule javaTimeModule() {
        return new JavaTimeModule();
    }

    /**
     * Jdk 8 time module.
     *
     * @return the jdk 8 module
     */
    @Bean
    public Jdk8Module jdk8TimeModule() {
        return new Jdk8Module();
    }

}
