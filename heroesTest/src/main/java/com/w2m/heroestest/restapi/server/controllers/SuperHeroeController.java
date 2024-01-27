package com.w2m.heroestest.restapi.server.controllers;

import com.w2m.heroestest.constants.RequestMappings;
import com.w2m.heroestest.config.aspects.annotations.LogExecutionTime;
import com.w2m.heroestest.model.domain.SuperHeroDomain;
import com.w2m.heroestest.model.enums.SuperPower;
import com.w2m.heroestest.restapi.server.mappers.SuperHeroRequestMapper;
import com.w2m.heroestest.restapi.server.mappers.SuperHeroResponseMapper;
import com.w2m.heroestest.restapi.server.requests.HeroRequest;
import com.w2m.heroestest.restapi.server.responses.HeroResponse;
import com.w2m.heroestest.restapi.services.SuperHeroesService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@RestController
@RequestMapping(RequestMappings.API + RequestMappings.SUPERHEROES)
public class SuperHeroeController extends BaseController {

    private static final Logger LOGGER = LogManager.getLogger(SuperHeroeController.class);

    @Autowired
    private SuperHeroesService superHeroesService;

    /**
     * Some javadoc here
     *
     * @return List
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Gets All the superheroes")
    //  @PreAuthorize("hasAnyAuthority('CONSULTANT_TYPE1')")
    @LogExecutionTime
    public ResponseEntity<List<HeroResponse>> getAllSuperHeroes() {
        LOGGER.debug("We can log whatever we need...");
        List<SuperHeroDomain> allheroes = this.superHeroesService.getAllSuperHeroes();
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allheroes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponses(allheroes), HttpStatus.OK);

    }

    @GetMapping("/page")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Pages All the superheroes")
    @LogExecutionTime
    //  @PreAuthorize("hasAnyAuthority('CONSULTANT_TYPE1')")
    public ResponseEntity<Page<HeroResponse>> pageAllSuperHeroes(@ParameterObject final Pageable pageable) {
        LOGGER.debug("We can log whatever we need...");

        Page<SuperHeroDomain> allheroes = this.superHeroesService.pageAllSuperHeroes(pageable);
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allheroes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponses(allheroes), HttpStatus.OK);

    }

    /**
     * Some javadoc here
     *
     * @return List
     */
    @GetMapping("/byName")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Gets All the superheroes by name. The name IS CASE SENSITIVE")
    @LogExecutionTime
    //  @PreAuthorize("hasAnyAuthority('CONSULTANT_TYPE1')")
    public ResponseEntity<List<HeroResponse>> getAllSuperHeroesByName(@RequestParam(required = true) String name) {
        LOGGER.debug("We can log whatever we need...");
        List<SuperHeroDomain> allheroes = this.superHeroesService.getAllSuperHeroesByName(name);
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allheroes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponses(allheroes), HttpStatus.OK);

    }

    /**
     * Some javadoc here
     *
     * @return List
     */
    @GetMapping("/byName/page")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Pages All the superheroes by name. The name ignores case sensitive")
    @LogExecutionTime
    //  @PreAuthorize("hasAnyAuthority('CONSULTANT_TYPE1')")
    public ResponseEntity<Page<HeroResponse>> pageAllSuperHeroesByName(@RequestParam(required = true) String name,
            @ParameterObject final Pageable pageable) {
        LOGGER.debug("We can log whatever we need...");
        Page<SuperHeroDomain> allheroes = this.superHeroesService.pageAllSuperHeroesByName(name, pageable);
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allheroes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponses(allheroes), HttpStatus.OK);

    }

    @GetMapping("/byPower")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Gets All the superheroes with a particular power")
    @LogExecutionTime
    public ResponseEntity<List<HeroResponse>> getAllSuperHeroesByPower(
            @RequestParam(required = true) SuperPower power) {
        LOGGER.debug("We can log whatever we need...");
        List<SuperHeroDomain> allheroes = this.superHeroesService.getAllSuperHeroesBySuperPower(power);
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allheroes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponses(allheroes), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Gets a superhero by his/her id")
    @LogExecutionTime
    //  @PreAuthorize("hasAnyAuthority('CONSULTANT_TYPE1')")
    public ResponseEntity<HeroResponse> getSuperHeroById(@PathVariable(value = "id", required = true) Long id) {
        LOGGER.debug("We can log whatever we need...");
        SuperHeroDomain hero = superHeroesService.findById(id);
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. I'll will not repeat this or implement on the other endpoints, is just an example.");
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponse(hero), HttpStatus.OK);
    }

    /**
     * Creates a new hero
     *
     * @param heroRequest the hero request data
     * @return the user created
     */
    @Operation(summary = "Creates a new hero")
    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    @LogExecutionTime
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<HeroResponse> createSuperHero(@Valid @RequestBody final HeroRequest heroRequest) {

        LOGGER.debug("Init - Create new hero", heroRequest.toString());
        SuperHeroDomain hero = superHeroesService.createSuperHero(
                SuperHeroRequestMapper.INSTANCE.fromRequestToDto(heroRequest));

        LOGGER.debug("End - Create new hero", Objects.isNull(hero) ? null : hero.toString());
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponse(hero), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Updates an existing hero")
    @LogExecutionTime
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<HeroResponse> updateSuperHero(@PathVariable(value = "id", required = true) Long id,
            @RequestBody HeroRequest heroRequest) {
        LOGGER.debug("Init - Update existing hero", heroRequest.toString());
        SuperHeroDomain hero = superHeroesService.updateSuperHero(id,
                SuperHeroRequestMapper.INSTANCE.fromRequestToDto(heroRequest));

        LOGGER.debug("End - Update existing hero", Objects.isNull(hero) ? null : hero.toString());
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponse(hero), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Adds a superpower to an existing hero")
    @LogExecutionTime
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<HeroResponse> addPowerToSuperHero(@PathVariable(value = "id", required = true) Long id,
            @RequestParam(required = true) SuperPower power) {
        LOGGER.debug("Init - Add extra power to hero with id", id);
        SuperHeroDomain hero = superHeroesService.addSuperPower(id, power);

        LOGGER.debug("End - Extra power to hero", Objects.isNull(hero) ? null : hero.toString());
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponse(hero), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Deletes a hero by his/her id")
    @LogExecutionTime
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> deleteSuperHero(@PathVariable("id") long id) {

        LOGGER.debug("Init - Delete hero with id", id);
        superHeroesService.deleteSuperHeroById(id);

        LOGGER.debug("End -  Delete hero with id", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Deletes all heroes")
    @LogExecutionTime
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<HttpStatus> deleteAllSuperHeros() {
        LOGGER.debug("Init - Delete All heroes");
        superHeroesService.deleteAllSuperHeros();

        LOGGER.debug("End - Delete All heroes");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
