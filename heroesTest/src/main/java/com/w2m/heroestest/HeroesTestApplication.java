package com.w2m.heroestest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author jruizh
 */
@SpringBootApplication
@EnableCaching
//@EnableScheduling
//@EnableJpaRepositories
@OpenAPIDefinition(info = @Info(title = "Superheroes SpringBoot 3 API", version = "1.0", description = "This is an amazing API to define and classify superheroes", termsOfService = "http://swagger.io/terms/", license = @License(name = "Apache 2.0 Licence for super heroes", url = "http://springdoc.org/")))
public class HeroesTestApplication {

    private static final Logger LOGGER = LogManager.getLogger(HeroesTestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HeroesTestApplication.class, args);
    }

}
