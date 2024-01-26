package com.w2m.heroestest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author jruizh
 */
@SpringBootApplication
//@EnableScheduling
//@EnableJpaRepositories
@OpenAPIDefinition(info = @Info(title = "Superheroes API", version = "2.0", description = "Superheroes Information"))
public class HeroesTestApplication {

    private static final Logger LOGGER = LogManager.getLogger(HeroesTestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HeroesTestApplication.class, args);
    }

}
