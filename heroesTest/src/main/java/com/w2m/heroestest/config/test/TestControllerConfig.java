package com.w2m.heroestest.config.test;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The Class TestControllerConfig.
 */
@Profile("controllerContext")
@Configuration
//@EnableWebSecurity
@EnableWebMvc
//@EnableMethodSecurity
public class TestControllerConfig {

    public static final String PROFILE = "controllerContext";

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
