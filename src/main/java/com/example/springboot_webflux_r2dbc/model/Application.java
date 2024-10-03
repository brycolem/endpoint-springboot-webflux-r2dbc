package com.example.springboot_webflux_r2dbc.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

@Data
@NoArgsConstructor
@Table("applications")
public class Application {

    @Id
    private Long id;

    private String employer;

    private String title;

    private String link;

    @Column("company_id")
    private Integer companyId;

    private List<Note> notes;
}
