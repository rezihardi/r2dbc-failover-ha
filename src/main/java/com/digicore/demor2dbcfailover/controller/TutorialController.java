package com.digicore.demor2dbcfailover.controller;

import com.digicore.demor2dbcfailover.model.PojoTutorial;
import com.digicore.demor2dbcfailover.model.Tutorial;
import com.digicore.demor2dbcfailover.service.TutorialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
@Tag(name = "Tutorial Bootcamp API")
public class TutorialController implements ErrorController {
    @Autowired
    TutorialService tutorialService;

    //header
    @Operation(summary = "Get a tutorial object by title", description = "API to return tutorial List")
    //mocking description
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Not found - The product was not found")
    })
    @GetMapping("/tutorials")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Tutorial> getAllTutorials(@RequestParam(required = false) String title) {
        if (title == null)
            return tutorialService.findAll();
        else
            return tutorialService.findByTitleContaining(title);
    }

    @GetMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Tutorial> getTutorialById(@PathVariable("id") int id) {
         return tutorialService.findById(id);
    }

    @PostMapping("/tutorials")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Tutorial> createTutorial(@RequestBody PojoTutorial tutorial) {
        return tutorialService.save(tutorial);
    }

    @PutMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Tutorial> updateTutorial(@PathVariable("id") int id, @RequestBody PojoTutorial pojoTutorial) {
        return tutorialService.update(id, pojoTutorial);
    }

    @DeleteMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteTutorial(@PathVariable("id") int id) {
        return tutorialService.deleteById(id);
    }

    @DeleteMapping("/tutorials")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteAllTutorials() {
        return tutorialService.deleteAll();
    }

    @GetMapping("/tutorials/published")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Tutorial> findByPublished() {
        return tutorialService.findByPublished(true);
    }
}
