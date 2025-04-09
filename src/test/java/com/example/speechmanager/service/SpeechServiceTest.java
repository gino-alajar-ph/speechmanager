package com.example.speechmanager.service;

import com.example.speechmanager.dto.SpeechDTO;
import com.example.speechmanager.exception.ResourceNotFoundException;
import com.example.speechmanager.mapper.SpeechMapper;
import com.example.speechmanager.model.Speech;
import com.example.speechmanager.repository.SpeechRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SpeechServiceTest {

    private SpeechRepository repo;
    private SpeechMapper mapper;
    private SpeechService service;

    @BeforeEach
    void setUp() {
        repo = mock(SpeechRepository.class);
        mapper = mock(SpeechMapper.class);
        service = new SpeechService(repo, mapper);
    }

    @Test
    void testGetAllSpeeches() {
        Speech entity = Instancio.create(Speech.class);
        SpeechDTO dto = Instancio.create(SpeechDTO.class);

        when(repo.findAll()).thenReturn(List.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        List<SpeechDTO> result = service.getAllSpeeches();

        assertThat(result).hasSize(1);
        verify(repo).findAll();
        verify(mapper).toDto(entity);
    }

    @Test
    void testAddSpeech() {
        SpeechDTO dto = Instancio.create(SpeechDTO.class);
        Speech entity = Instancio.create(Speech.class);

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(repo.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        SpeechDTO result = service.addSpeech(dto);

        assertThat(result).isNotNull();
        verify(mapper).toEntity(dto);
        verify(repo).save(entity);
        verify(mapper).toDto(entity);
    }

    @Test
    void testUpdateSpeech_whenFound() {
        Long id = 1L;
        SpeechDTO dto = Instancio.create(SpeechDTO.class);
        Speech existing = new Speech();
        existing.setId(id);

        when(repo.findById(id)).thenReturn(Optional.of(existing));
        when(repo.save(any())).thenReturn(existing);
        when(mapper.toDto(existing)).thenReturn(dto);

        SpeechDTO result = service.updateSpeech(id, dto);

        assertThat(result).isNotNull();
        verify(repo).findById(id);
        verify(repo).save(existing);
        verify(mapper).toDto(existing);
    }

    @Test
    void testUpdateSpeech_whenNotFound() {
        Long id = 99L;
        SpeechDTO dto = Instancio.create(SpeechDTO.class);
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateSpeech(id, dto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void testDeleteSpeech_whenFound() {
        Long id = 10L;
        Speech speech = new Speech();
        speech.setId(id);

        when(repo.findById(id)).thenReturn(Optional.of(speech));

        service.deleteSpeech(id);

        verify(repo).delete(speech);
    }

    @Test
    void testDeleteSpeech_whenNotFound() {
        Long id = 42L;
        when(repo.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteSpeech(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("not found");
    }

    @Test
    void testSearch_shouldReturnResults() {
        Speech speech = Instancio.create(Speech.class);
        SpeechDTO dto = Instancio.create(SpeechDTO.class);

        when(repo.findAll(any(Specification.class))).thenReturn(List.of(speech));
        when(mapper.toDto(speech)).thenReturn(dto);

        List<SpeechDTO> results = service.search("John", LocalDate.now().minusDays(1), LocalDate.now(), "freedom");

        assertThat(results).hasSize(1);
        verify(repo).findAll(any(Specification.class));
    }

    @Test
    void testSearch_shouldReturnEmpty() {
        when(repo.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        List<SpeechDTO> results = service.search("Nobody", null, null, null);

        assertThat(results).isEmpty();
    }
}
