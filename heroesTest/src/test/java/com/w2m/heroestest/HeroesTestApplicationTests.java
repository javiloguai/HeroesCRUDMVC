package com.w2m.heroestest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ContextConfiguration
@ExtendWith({ SpringExtension.class, MockitoExtension.class })
//@WithMockUser(username="admin",roles={"USER","ADMIN"})
class HeroesTestApplicationTests {

    @Test
    void contextLoads() {
        Assertions.assertTrue(true);
    }

}
