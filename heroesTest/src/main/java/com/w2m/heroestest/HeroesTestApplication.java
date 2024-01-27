package com.w2m.heroestest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author jruizh
 */
@SpringBootApplication
@EnableCaching
//@EnableScheduling
@EnableJpaRepositories
public class HeroesTestApplication {

    private static final Logger LOGGER = LogManager.getLogger(HeroesTestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HeroesTestApplication.class, args);
    }

}
