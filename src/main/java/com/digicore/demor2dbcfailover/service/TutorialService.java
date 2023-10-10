package com.digicore.demor2dbcfailover.service;

import com.digicore.demor2dbcfailover.model.PojoTutorial;
import com.digicore.demor2dbcfailover.model.Tutorial;
import com.digicore.demor2dbcfailover.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class TutorialService {

    @Autowired
    TutorialRepository tutorialRepository;

    public Flux<Tutorial> findAll(){
        return tutorialRepository.findAll();
    }

    public Flux<Tutorial> findByTitleContaining(String title){
        return tutorialRepository.findByTitleContaining(title);
    }

    public Mono<Tutorial> findById(int id) {
        return tutorialRepository.findById(id);
    }

    public Mono<Tutorial> save(PojoTutorial pojoTutorial) {
        Tutorial tutorial= new Tutorial();
        tutorial.setDescription(pojoTutorial.getDescription());
        tutorial.setTitle(pojoTutorial.getTitle());
        tutorial.setPublished(false);
        return tutorialRepository.save(tutorial);
    }

    public Mono<Tutorial> update(int id, PojoTutorial pojoTutorial) {
        Tutorial tutorial = new Tutorial();
        tutorial.setPublished(false);
        tutorial.setDescription(pojoTutorial.getDescription());
        tutorial.setTitle(pojoTutorial.getTitle());
        return tutorialRepository.findById(id).map(Optional::of).defaultIfEmpty(Optional.empty()).flatMap(optionalTutorial -> {
                    if (optionalTutorial.isPresent()) {
                        tutorial.setId(id);
                        return tutorialRepository.save(tutorial);
                    }
                    return Mono.empty();
                });
    }

    public Mono<Void> deleteById(int id) {
        return tutorialRepository.deleteById(id);
    }

    public Mono<Void> deleteAll() {
        return tutorialRepository.deleteAll();
    }

    public Flux<Tutorial> findByPublished(boolean isPublished) {
        return tutorialRepository.findByPublished(isPublished);
    }
}
