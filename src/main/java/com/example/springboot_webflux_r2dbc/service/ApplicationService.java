package com.example.springboot_webflux_r2dbc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.springboot_webflux_r2dbc.model.Application;
import com.example.springboot_webflux_r2dbc.model.Note;
import com.example.springboot_webflux_r2dbc.repository.ApplicationRepository;
import com.example.springboot_webflux_r2dbc.repository.NoteRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final NoteRepository noteRepository;

    public ApplicationService(ApplicationRepository applicationRepository, NoteRepository noteRepository) {
        this.applicationRepository = applicationRepository;
        this.noteRepository = noteRepository;
    }

    public Flux<Application> getAllApplications() {
        return applicationRepository.findAllWithNotes()
                .collectList()
                .flatMapMany(applications -> {
                    List<Long> applicationIds = applications.stream().map(Application::getId)
                            .collect(Collectors.toList());

                    return noteRepository.findByApplicationIds(applicationIds)
                            .collectList()
                            .flatMapMany(notes -> {
                                Map<Long, List<Note>> notesByApplicationId = notes.stream()
                                        .collect(Collectors.groupingBy(Note::getApplicationId));

                                return Flux.fromIterable(applications)
                                        .map(application -> {
                                            application.setNotes(notesByApplicationId.getOrDefault(application.getId(),
                                                    new ArrayList<>()));
                                            return application;
                                        });
                            });
                });
    }

    public Mono<Application> createApplication(Application application) {
        return applicationRepository.save(application);
    }

    /*
     * public Mono<Application> getApplication(Long id) {
     * return applicationRepository.findById(id)
     * .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND,
     * "Application not found")))
     * .flatMap(application ->
     * noteRepository.findByApplicationId(application.getId())
     * .collectList()
     * .map(notes -> {
     * application.setNotes(notes);
     * return application;
     * }));
     * }
     */
}
