package com.example.springboot_webflux_r2dbc.repository;

import java.util.List;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.example.springboot_webflux_r2dbc.model.Note;
import reactor.core.publisher.Flux;

public interface NoteRepository extends ReactiveCrudRepository<Note, Long> {
    @Query("SELECT * FROM notes WHERE application_id IN (:applicationIds)")
    Flux<Note> findByApplicationIds(List<Long> applicationIds);
}
