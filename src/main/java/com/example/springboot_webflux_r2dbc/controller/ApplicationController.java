package com.example.springboot_webflux_r2dbc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import com.example.springboot_webflux_r2dbc.model.Application;
import com.example.springboot_webflux_r2dbc.service.ApplicationService;

@RestController
@RequestMapping("/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public Flux<Application> getAllApplications() {
        return applicationService.getAllApplications();
    }

    @PostMapping
    public Mono<ResponseEntity<Application>> createApplication(@RequestBody Application application) {
        return applicationService.createApplication(application)
                .map(createdApplication -> new ResponseEntity<>(createdApplication, HttpStatus.CREATED));
    }

    /*
     * @GetMapping("/{id}")
     * public Mono<ResponseEntity<Application>> getApplication(@PathVariable Long
     * id) {
     * return applicationService.getApplication(id)
     * .map(application -> new ResponseEntity<>(application, HttpStatus.OK))
     * .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
     * }
     */

}
