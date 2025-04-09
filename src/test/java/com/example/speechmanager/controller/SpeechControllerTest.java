package com.example.speechmanager.controller;

import com.example.speechmanager.dto.SpeechDTO;
import com.example.speechmanager.service.SpeechService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SpeechController.class)
class SpeechControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpeechService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/speeches - should return all speeches")
    void getAllSpeeches() throws Exception {
        SpeechDTO dto = Instancio.create(SpeechDTO.class);
        Mockito.when(service.getAllSpeeches()).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/speeches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value(dto.getAuthor()));
    }

    @Test
    @DisplayName("POST /api/speeches - should create a new speech")
    void createSpeech() throws Exception {
        SpeechDTO dto = Instancio.create(SpeechDTO.class);

        Mockito.when(service.addSpeech(any(SpeechDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/speeches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(dto.getTitle()));
    }

    @Test
    @DisplayName("PUT /api/speeches/{id} - should update a speech")
    void updateSpeech() throws Exception {
        SpeechDTO dto = Instancio.create(SpeechDTO.class);
        Long id = 1L;

        Mockito.when(service.updateSpeech(eq(id), any(SpeechDTO.class))).thenReturn(dto);

        mockMvc.perform(put("/api/speeches/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(dto.getAuthor()));
    }

    @Test
    @DisplayName("DELETE /api/speeches/{id} - should delete a speech")
    void deleteSpeech() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/api/speeches/{id}", id))
                .andExpect(status().isNoContent());

        Mockito.verify(service).deleteSpeech(id);
    }

    @Test
    @DisplayName("GET /api/speeches/search - should return matching speeches")
    void searchSpeeches() throws Exception {
        SpeechDTO dto = Instancio.create(SpeechDTO.class);
        Mockito.when(service.search(any(), any(), any(), any()))
                .thenReturn(Collections.singletonList(dto));

        mockMvc.perform(get("/api/speeches/search")
                        .param("author", dto.getAuthor())
                        .param("startDate", LocalDate.now().minusDays(5).toString())
                        .param("endDate", LocalDate.now().toString())
                        .param("keyword", "example"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].author").value(dto.getAuthor()));
    }
}
