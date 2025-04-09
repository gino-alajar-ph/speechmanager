package com.example.speechmanager.service;

import com.example.speechmanager.dto.SpeechDTO;
import com.example.speechmanager.exception.ResourceNotFoundException;
import com.example.speechmanager.mapper.SpeechMapper;
import com.example.speechmanager.model.Speech;
import com.example.speechmanager.repository.SpeechRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpeechService {

    private final SpeechRepository repo;
    private final SpeechMapper mapper;

    public List<SpeechDTO> getAllSpeeches() {
        return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public SpeechDTO addSpeech(SpeechDTO dto) {
        System.out.println("TEST >>>>" + dto.getAuthor());
        Speech speech = mapper.toEntity(dto);

        System.out.println("NAME >>>>>>>>>>>>" + speech.getAuthor());
        return mapper.toDto(repo.save(mapper.toEntity(dto)));
    }

    public SpeechDTO updateSpeech(Long id, SpeechDTO dto) {
        Speech existing = repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Speech with ID " + id + " not found"));
        existing.setAuthor(dto.getAuthor());
        existing.setTitle(dto.getTitle());
        existing.setBody(dto.getBody());
        existing.setSpeechDate(dto.getSpeechDate());
        existing.setKeywords(dto.getKeywords());
        return mapper.toDto(repo.save(existing));
    }

    public void deleteSpeech(Long id) {
        Speech existing = repo.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Speech with ID " + id + " not found"));
        repo.delete(existing);
    }

    public List<SpeechDTO> search(String author, LocalDate startDate, LocalDate endDate, String keyword) {
        Specification<Speech> spec = (root, query, cb) -> {
            Predicate p = cb.conjunction();
            if (author != null) p = cb.and(p, cb.equal(root.get("author"), author));
            if (startDate != null) p = cb.and(p, cb.greaterThanOrEqualTo(root.get("speechDate"), startDate));
            if (endDate != null) p = cb.and(p, cb.lessThanOrEqualTo(root.get("speechDate"), endDate));
            if (keyword != null) p = cb.and(p, cb.like(cb.lower(root.get("body")), "%" + keyword.toLowerCase() + "%"));
            return p;
        };
        return repo.findAll(spec).stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
