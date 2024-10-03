package com.example.springboot_webflux_r2dbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@Table("notes")
public class Note {

    @Id
    private Long id;

    @Column("note_text")
    private String noteText;

    @Column("application_id")
    private Long applicationId;
}
