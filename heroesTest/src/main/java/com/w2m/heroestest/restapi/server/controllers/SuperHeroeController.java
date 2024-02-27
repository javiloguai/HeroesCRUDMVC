package com.w2m.heroestest.restapi.server.controllers;

import com.w2m.heroestest.config.aspects.annotations.LogExecutionTime;
import com.w2m.heroestest.constants.RequestMappings;
import com.w2m.heroestest.model.domain.SuperHeroDomain;
import com.w2m.heroestest.model.enums.SuperPower;
import com.w2m.heroestest.restapi.server.assemblers.HeroPageModelAssembler;
import com.w2m.heroestest.restapi.server.mappers.SuperHeroRequestMapper;
import com.w2m.heroestest.restapi.server.mappers.SuperHeroResponseMapper;
import com.w2m.heroestest.restapi.server.requests.HeroRequest;
import com.w2m.heroestest.restapi.server.responses.HeroResponse;
import com.w2m.heroestest.restapi.services.SuperHeroesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

    @Autowired
    private PagedResourcesAssembler<SuperHeroDomain> pagedResourcesAssembler;

    @Autowired
    private HeroPageModelAssembler heroPageModelAssembler;

    /**
     * Some javadoc here
     *
     * @return List
     */
    @GetMapping
    //@ResponseStatus(code = HttpStatus.OK)
    @Operation(summary = "Gets All the superheroes", description = "Gets All the superheroes.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of superheroes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HeroResponse.class)))),
            @ApiResponse(responseCode = "204", description = "EmptyList. Any superhero found.", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "403", description = "Forbidden.", content = {
                    @Content(schema = @Schema()) }) })
    @LogExecutionTime
    public ResponseEntity<List<HeroResponse>> getAllSuperHeroes() {
        LOGGER.debug("We can log whatever we need...");
        List<SuperHeroDomain> allheroes = this.superHeroesService.getAllSuperHeroes();
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. " + "I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allheroes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponses(allheroes), HttpStatus.OK);

    }

    @GetMapping("/page")
    @Operation(summary = "Pages All the superheroes", description = "Gets All the superheroes paginated.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of superheroes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HeroResponse.class)))),
            @ApiResponse(responseCode = "204", description = "EmptyList. Any superhero found.", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "403", description = "Forbidden.", content = {
                    @Content(schema = @Schema()) }) })
    @LogExecutionTime
    public ResponseEntity<PagedModel<HeroResponse>> pageAllSuperHeroes(@ParameterObject final Pageable pageable) {
        LOGGER.debug("We can log whatever we need...");

        Page<SuperHeroDomain> allheroes = this.superHeroesService.pageAllSuperHeroes(pageable);
        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. " + "I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allheroes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        final PagedModel<HeroResponse> response = pagedResourcesAssembler.toModel(allheroes, heroPageModelAssembler);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    /**
     * Some javadoc here
     *
     * @return List
     */
    @GetMapping("/byName")
    @Operation(summary = "Gets By Name", description = "Gets All the superheroes by name. The name IS CASE SENSITIVE.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of superheroes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HeroResponse.class)))),
            @ApiResponse(responseCode = "204", description = "EmptyList. Any superhero found.", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "403", description = "Forbidden.", content = {
                    @Content(schema = @Schema()) }) })
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
    @Operation(summary = "Page By Name", description = "Pages All the superheroes by name. The name ignores case sensitive.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of superheroes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HeroResponse.class)))),
            @ApiResponse(responseCode = "204", description = "EmptyList. Any superhero found.", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "403", description = "Forbidden.", content = {
                    @Content(schema = @Schema()) }) })
    @LogExecutionTime
    public ResponseEntity<PagedModel<HeroResponse>> pageAllSuperHeroesByName(@RequestParam(required = true) String name,
            @ParameterObject final Pageable pageable) {
        LOGGER.debug("We can log whatever we need...");
        Page<SuperHeroDomain> allheroes = this.superHeroesService.pageAllSuperHeroesByName(name, pageable);

        LOGGER.debug(
                "If it's necessary to pick apart the business objects from the response objects we could deal with a mapper here. I'll will not repeat this or implement on the other endpoints, is just an example.");
        if (allheroes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        final PagedModel<HeroResponse> response = pagedResourcesAssembler.toModel(allheroes, heroPageModelAssembler);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/byPower")
    @Operation(summary = "Gets by power", description = "Gets All the superheroes with a particular power")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of superheroes", content = @Content(array = @ArraySchema(schema = @Schema(implementation = HeroResponse.class)))),
            @ApiResponse(responseCode = "204", description = "EmptyList. Any superhero found.", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "403", description = "Forbidden.", content = {
                    @Content(schema = @Schema()) }) })
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
    @Operation(summary = "Get by id", description = "Gets a superhero by his/her id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of the superhero", content = {
                    @Content(schema = @Schema(implementation = HeroResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Any superhero found with the given Id.", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "403", description = "Forbidden.", content = {
                    @Content(schema = @Schema()) }) })
    @LogExecutionTime
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
    @Operation(summary = "Create Hero", description = "This api is used to Creates a new hero into DB")
    @ApiResponses({ @ApiResponse(responseCode = "201", description = "HHero successfully added to the DB", content = {
            @Content(schema = @Schema(implementation = HeroResponse.class)) }),
            @ApiResponse(responseCode = "406", description = "Hero superpowers list cannot be empty", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "409", description = "Hero Already exist with given name", content = {
                    @Content(schema = @Schema()) }) })
    @PostMapping
    @Transactional(propagation = Propagation.REQUIRED)
    @LogExecutionTime
//    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<HeroResponse> createSuperHero(@Valid @RequestBody final HeroRequest heroRequest) {

        LOGGER.debug(String.format("Init - Create new hero %s", heroRequest.toString()));
        SuperHeroDomain hero = superHeroesService.createSuperHero(
                SuperHeroRequestMapper.INSTANCE.fromRequestToDto(heroRequest));

        LOGGER.debug(String.format("End - Create new hero %s", Objects.isNull(hero) ? null : hero.toString()));
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponse(hero), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Update Hero", description = "Updates an existing hero details by providing the id.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Hero details are successfully updated into the DB", content = {
                    @Content(schema = @Schema(implementation = HeroResponse.class)) }),
            @ApiResponse(responseCode = "409", description = "Hero Already exist with given name", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "406", description = "Hero superpowers list cannot be empty", content = {
                    @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Hero not found for the given id", content = {
                    @Content(schema = @Schema()) }) })
    @LogExecutionTime
    public ResponseEntity<HeroResponse> updateSuperHero(@PathVariable(value = "id", required = true) Long id,
            @RequestBody HeroRequest heroRequest) {
        LOGGER.debug(String.format("Init - Update existing hero %s", heroRequest.toString()));
        SuperHeroDomain hero = superHeroesService.updateSuperHero(id,
                SuperHeroRequestMapper.INSTANCE.fromRequestToDto(heroRequest));

        LOGGER.debug(String.format("End - Update existing hero %s", Objects.isNull(hero) ? null : hero.toString()));
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponse(hero), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Add Power", description = "Adds a superpower to an existing hero by providing its id")
    @ApiResponses({ @ApiResponse(responseCode = "200", description = "Hero power is added", content = {
            @Content(schema = @Schema(implementation = HeroResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Hero not found for the given id", content = {
                    @Content(schema = @Schema()) }) })
    @LogExecutionTime
    public ResponseEntity<HeroResponse> addPowerToSuperHero(@PathVariable(value = "id", required = true) Long id,
            @RequestParam(required = true) SuperPower power) {
        LOGGER.debug(String.format("Init - Add extra power to hero with id %s", id));
        SuperHeroDomain hero = superHeroesService.addSuperPower(id, power);

        LOGGER.debug(String.format("End - Extra power to hero %s", Objects.isNull(hero) ? null : hero.toString()));
        return new ResponseEntity<>(SuperHeroResponseMapper.INSTANCE.toResponse(hero), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Deletes Hero", description = "Deletes a hero by his/her id")
    @ApiResponses({ @ApiResponse(responseCode = "204", description = "Hero is deleted", content = {
            @Content(schema = @Schema(implementation = HeroResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Hero not found for the given id", content = {
                    @Content(schema = @Schema()) }) })
    @LogExecutionTime
    public ResponseEntity<HttpStatus> deleteSuperHero(@PathVariable("id") long id) {

        LOGGER.debug(String.format("Init - Delete hero with id %s", id));
        superHeroesService.deleteSuperHeroById(id);

        LOGGER.debug(String.format("End -  Delete hero with id %s", id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping
    @Transactional(propagation = Propagation.REQUIRED)
    @Operation(summary = "Delete all heroes", description = "Deletes all heroes")
    @ApiResponse(responseCode = "204", description = "Hero is deleted or does nothing if Hero list is empty", content = {
            @Content(schema = @Schema()) })
    @LogExecutionTime
    public ResponseEntity<HttpStatus> deleteAllSuperHeroes() {
        LOGGER.debug("Init - Delete All heroes");
        superHeroesService.deleteAllSuperHeroes();

        LOGGER.debug("End - Delete All heroes");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
