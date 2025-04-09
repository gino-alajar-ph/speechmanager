package com.example.speechmanager.controller;

import com.example.speechmanager.dto.SpeechDTO;
import com.example.speechmanager.service.SpeechService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/speeches")
@Tag(name = "Speech API", description = "Manage political speeches")
@Validated
public class SpeechController {

    private final SpeechService service;

    public SpeechController(SpeechService service) {
        this.service = service;
    }

    @Operation(summary = "Get all speeches")
    @GetMapping
    public ResponseEntity<List<SpeechDTO>> getAllSpeeches() {
        return ResponseEntity.ok(service.getAllSpeeches());
    }

    @Operation(summary = "Create a new speech")
    @PostMapping
    public ResponseEntity<SpeechDTO> createSpeech(@Valid @RequestBody SpeechDTO dto) {
        SpeechDTO created = service.addSpeech(dto);
        return ResponseEntity.status(201).body(created); // 201 CREATED
    }

    @Operation(summary = "Update a speech by ID")
    @PutMapping("/{id}")
    public ResponseEntity<SpeechDTO> updateSpeech(
            @Parameter(description = "ID of the speech to update") @PathVariable Long id,
            @Valid @RequestBody SpeechDTO dto) {
        return ResponseEntity.ok(service.updateSpeech(id, dto));
    }

    @Operation(summary = "Delete a speech by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpeech(
            @Parameter(description = "ID of the speech to delete") @PathVariable Long id) {
        service.deleteSpeech(id);
        return ResponseEntity.noContent().build(); // 204 NO_CONTENT
    }

    @Operation(summary = "Search speeches with filters")
    @GetMapping("/search")
    public ResponseEntity<List<SpeechDTO>> search(
            @Parameter(description = "Filter by author") @RequestParam(required = false) String author,
            @Parameter(description = "Start date (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(description = "Keyword in body text") @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(service.search(author, startDate, endDate, keyword));
    }
}
