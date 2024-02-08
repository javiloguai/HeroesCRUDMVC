package com.w2m.heroestest;

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

    public static void main(String[] args) {
        SpringApplication.run(HeroesTestApplication.class, args);
    }

}
