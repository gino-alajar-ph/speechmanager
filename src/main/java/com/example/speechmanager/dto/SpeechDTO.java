package com.example.speechmanager.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SpeechDTO {
    private Long id;
    private String title;
    private String body;
    private String author;
    private LocalDate speechDate;
    private List<String> keywords;
}
