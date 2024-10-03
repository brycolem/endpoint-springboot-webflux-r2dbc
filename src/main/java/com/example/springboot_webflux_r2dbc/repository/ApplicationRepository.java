package com.example.springboot_webflux_r2dbc.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.example.springboot_webflux_r2dbc.model.Application;

import reactor.core.publisher.Flux;

public interface ApplicationRepository extends ReactiveCrudRepository<Application, Long> {
    @Query("SELECT * FROM applications")
    Flux<Application> findAllWithNotes();
}
