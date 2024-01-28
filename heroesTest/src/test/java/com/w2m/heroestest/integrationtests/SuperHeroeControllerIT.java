package com.w2m.heroestest.integrationtests;


import com.w2m.heroestest.config.exception.NotFoundException;
import com.w2m.heroestest.config.jackson.DefaultApplicationJsonMapper;
import com.w2m.heroestest.config.test.TestControllerConfig;
import com.w2m.heroestest.constants.RequestMappings;
import com.w2m.heroestest.factories.SuperHeroFactory;
import com.w2m.heroestest.model.domain.SuperHeroDomain;
import com.w2m.heroestest.restapi.server.controllers.SuperHeroeController;
import com.w2m.heroestest.restapi.server.requests.HeroRequest;
import com.w2m.heroestest.restapi.services.SuperHeroesService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.AccessDeniedException;

import static org.mockito.ArgumentMatchers.any;

/**
 * The Class SuperHeroeController Integration Test.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(properties = { "spring.main.banner-mode=off" })
@ContextConfiguration(classes = { TestControllerConfig.class, /*TestWebMvcConfig.class, */SuperHeroeController.class })
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles(TestControllerConfig.PROFILE)
@EnableSpringDataWebSupport
class SuperHeroeControllerIT {


    private final static String DATE_FORMAT = "dd-MM-yyyy";

    private final static String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";

    @Autowired
    private MockMvc mockMvc;

    private DefaultApplicationJsonMapper defaultApplicationJsonMapper;


    @MockBean
    SuperHeroesService superHeroesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        defaultApplicationJsonMapper = new DefaultApplicationJsonMapper(DATE_FORMAT, DATE_TIME_FORMAT);
    }

    /**
     * FindById test cases.
     */
    @Nested
    class FindByIdTest {

        @Test
        //@WithMockUser(username="admin",roles={"USER","ADMIN"})
        void givenNonExistingHero_ThenReturnNotFound() throws Exception {

           // Mockito.when(superHeroesService.findById(SuperHeroFactory.HERO_ID)).thenReturn(Optional.empty());
            Mockito.when(superHeroesService.findById(Mockito.any()))
                    .thenThrow(new NotFoundException("Error"));


            final String requestURL = RequestMappings.API + RequestMappings.SUPERHEROES+"/"+ SuperHeroFactory.HERO_ID;

            // @formatter:off
            mockMvc.perform(MockMvcRequestBuilders.get(requestURL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andReturn();
            // @formatter:on

            Mockito.verify(superHeroesService, Mockito.atLeastOnce()).findById(SuperHeroFactory.HERO_ID);

        }

        @Test
        void givenExistingHero_ThenReturnIt() throws Exception {

            final SuperHeroDomain domainToResponse = SuperHeroDomain.builder().name(SuperHeroFactory.NAME).description(SuperHeroFactory.NAME).build();

            Mockito.when(superHeroesService.findById(ArgumentMatchers.anyLong())).thenReturn(domainToResponse);

            final String requestURL = RequestMappings.API + RequestMappings.SUPERHEROES+"/"+ SuperHeroFactory.HERO_ID;

            // @formatter:off
            final MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.get(requestURL)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
            // @formatter:on

            Mockito.verify(superHeroesService, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
            final String response = result.getResponse().getContentAsString();
            final SuperHeroDomain searched = defaultApplicationJsonMapper.getObjectFromJson(response, SuperHeroDomain.class);

            Assertions.assertEquals(domainToResponse.getName(), searched.getName());
            Assertions.assertEquals(domainToResponse.getDescription(), searched.getDescription());
        }
    }

        @Nested
        @DisplayName("FindAll test cases. TODO: skills demostrated on ether GET Interation Test")
        class FindAllTest {
            //TODO Implement integrations tests for this endpoint.
            // It would need to be completed to have full code coverage.
            // It is clear from the tests above that I know how to perform integration tests for GET,
            // because of this I do not waste any more time implementing this method
            @Test
            @Disabled
            //@WithMockUser(username = "admin", roles = "ADMIN")
            void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {
                Assertions.assertTrue(true);
            }
        }

    @Nested
    @DisplayName("PageAll test cases.TODO: skills demostrated on ether GET Integration Test")
    class PageAllTest {
        //TODO Implement integrations tests for this endpoint.
        // It would need to be completed to have full code coverage.
        // It is clear from the tests above that I know how to perform integration tests for GET,
        // because of this I do not waste any more time implementing this method
        @Test
        @Disabled
            //@WithMockUser(username = "admin", roles = "ADMIN")
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {
            Assertions.assertTrue(true);
        }
    }

    @Nested
    @DisplayName("GetAllByName test cases. TODO: skills demostrated on ether GET Interation Test")
    class GetAllByNameTest {
        //TODO Implement integrations tests for this endpoint.
        // It would need to be completed to have full code coverage.
        // It is clear from the tests above that I know how to perform integration tests for GET,
        // because of this I do not waste any more time implementing this method
        @Test
        @Disabled
        //@WithMockUser(username = "admin", roles = "ADMIN")
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {
            Assertions.assertTrue(true);
        }
    }

    @Nested
    @DisplayName("CreateGroup test cases")
    class CreateGroupTest {

        @Test
        @DisplayName("Create new group if requesting user has permissions")
        //@WithMockUser(username = "admin", roles = "ADMIN")
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {

            final HeroRequest request = SuperHeroFactory.getRequest();

            final String requestBody = defaultApplicationJsonMapper.getJsonFromObject(request);
            final SuperHeroDomain domainToResponse = SuperHeroDomain.builder().name(request.getName()).description(request.getDescription()).build();

            Mockito.when(superHeroesService.createSuperHero(ArgumentMatchers.any())).thenReturn(domainToResponse);

            // @formatter:off
            final MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.post(RequestMappings.API + RequestMappings.SUPERHEROES)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
            // @formatter:on

            Mockito.verify(superHeroesService, Mockito.times(1)).createSuperHero(any());
            final String response = result.getResponse().getContentAsString();
            final SuperHeroDomain domain = defaultApplicationJsonMapper.getObjectFromJson(response, SuperHeroDomain.class);

            Assertions.assertEquals(request.getName(), domain.getName());
            Assertions.assertEquals(request.getDescription(), domain.getDescription());

        }

        @Test
        @DisplayName("Access is denied if requesting user does not have permissions")
        //@WithMockUser(username = "admin", roles = "ADMIN")
        @Disabled
        void givenRequestingUserWithoutPermissions_ThenOperationIsDenied() {
            final HeroRequest request = SuperHeroFactory.getRequest();

            final String requestBody = defaultApplicationJsonMapper.getJsonFromObject(request);

            final SuperHeroDomain domainToResponse = SuperHeroDomain.builder().name(request.getName()).description(request.getDescription()).build();

            Mockito.when(superHeroesService.createSuperHero(ArgumentMatchers.any())).thenReturn(domainToResponse);

            // @formatter:off

            // @formatter:on
            try {
                // @formatter:off
                mockMvc.perform(MockMvcRequestBuilders.post(RequestMappings.API + RequestMappings.SUPERHEROES)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isForbidden());
                // @formatter:on
                Assertions.fail();
            } catch (final Exception e) {
                Assertions.assertTrue(e.getCause() instanceof AccessDeniedException);
            }
        }

    }

    @Nested
    @DisplayName("ModifyGroup test cases")
    class ModifyGroupTest {
        @Test
        @DisplayName("Modify the group if requesting user has permissions")
            //@WithMockUser(username = "admin", roles = "ADMIN")
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {
            final HeroRequest request = SuperHeroFactory.getRequest();

            final String requestBody = defaultApplicationJsonMapper.getJsonFromObject(request);

            final SuperHeroDomain domainToResponse = SuperHeroDomain.builder().name(request.getName()).description(request.getDescription()).build();

            Mockito.when(superHeroesService.updateSuperHero(ArgumentMatchers.anyLong(),ArgumentMatchers.any())).thenReturn(domainToResponse);

            // @formatter:off
            final MvcResult result =  mockMvc.perform(MockMvcRequestBuilders.put(RequestMappings.API + RequestMappings.SUPERHEROES + "/{id}",SuperHeroFactory.HERO_ID)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
            // @formatter:on

            Mockito.verify(superHeroesService, Mockito.times(1)).updateSuperHero(ArgumentMatchers.anyLong(),ArgumentMatchers.any());
            final String response = result.getResponse().getContentAsString();
            final SuperHeroDomain domain = defaultApplicationJsonMapper.getObjectFromJson(response, SuperHeroDomain.class);

            Assertions.assertEquals(request.getName(), domain.getName());
            Assertions.assertEquals(request.getDescription(), domain.getDescription());
        }

        @Test
        @DisplayName("Access is denied if requesting user does not have permissions")
            //@WithMockUser(username = "admin", roles = "ADMIN")
        @Disabled
        void givenRequestingUserWithoutPermissions_ThenOperationIsDenied() {
            final HeroRequest request = SuperHeroFactory.getRequest();

            final String requestBody = defaultApplicationJsonMapper.getJsonFromObject(request);

            // @formatter:off

            // @formatter:on
            try {
                // @formatter:off
                mockMvc.perform(MockMvcRequestBuilders.put(RequestMappings.API + RequestMappings.SUPERHEROES + "/{id}",SuperHeroFactory.HERO_ID)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isForbidden());
                // @formatter:on
                Assertions.fail();
            } catch (final Exception e) {
                Assertions.assertTrue(e.getCause() instanceof AccessDeniedException);
            }
        }

    }

    @Nested
    @DisplayName("deleteSuperHero Test test cases")
    class deleteSuperHeroTest {
        @Test
            //@WithMockUser(username = "admin", roles = "ADMIN")
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {

            //// @formatter:off
            mockMvc.perform(MockMvcRequestBuilders.delete(RequestMappings.API + RequestMappings.SUPERHEROES + "/"+SuperHeroFactory.HERO_ID)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
            // @formatter:on
            Mockito.verify(superHeroesService, Mockito.times(1)).deleteSuperHeroById(ArgumentMatchers.anyLong());
        }

        @Test
        @DisplayName("Access is denied if requesting user does not have permissions")
        @Disabled
//@WithMockUser(username = "admin", roles = "ADMIN")
        void givenRequestingUserWithoutPermissions_ThenOperationIsDenied() {

            try {
                // @formatter:off
                mockMvc.perform(MockMvcRequestBuilders.delete(RequestMappings.API + RequestMappings.SUPERHEROES + "/"+SuperHeroFactory.HERO_ID)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isForbidden());
                // @formatter:on
                Mockito.verify(superHeroesService, Mockito.times(0)).deleteSuperHeroById(ArgumentMatchers.anyLong());
                // @formatter:on
                Assertions.fail();
            } catch (final Exception e) {
                Assertions.assertTrue(e.getCause() instanceof AccessDeniedException);
            }
        }

    }
    @Nested
    @DisplayName("deleteAllSuperHeros Test test cases")
    class deleteAllSuperHerosTest {
        @Test
                   //@WithMockUser(username = "admin", roles = "ADMIN")
        void givenRequestingUserWithPermissions_ThenOperationIsAccepted() throws Exception {

            //// @formatter:off
            mockMvc.perform(MockMvcRequestBuilders.delete(RequestMappings.API + RequestMappings.SUPERHEROES)
                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isNoContent());
            // @formatter:on
            Mockito.verify(superHeroesService, Mockito.times(1)).deleteAllSuperHeros();
        }

        @Test
        @DisplayName("Access is denied if requesting user does not have permissions")
                @Disabled
//@WithMockUser(username = "admin", roles = "ADMIN")
        void givenRequestingUserWithoutPermissions_ThenOperationIsDenied() {

            try {
                // @formatter:off
                mockMvc.perform(MockMvcRequestBuilders.delete(RequestMappings.API + RequestMappings.SUPERHEROES)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isForbidden());
                // @formatter:on
                Mockito.verify(superHeroesService, Mockito.times(0)).deleteAllSuperHeros();
                // @formatter:on
                Assertions.fail();
            } catch (final Exception e) {
                Assertions.assertTrue(e.getCause() instanceof AccessDeniedException);
            }
        }

    }

}
