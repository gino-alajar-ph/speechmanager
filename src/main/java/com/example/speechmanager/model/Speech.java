package com.example.speechmanager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Speech {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Lob
    private String body;
    private String author;
    private LocalDate speechDate;

    @ElementCollection
    private List<String> keywords;

}
