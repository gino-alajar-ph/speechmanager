package com.example.speechmanager.mapper;

import com.example.speechmanager.dto.SpeechDTO;
import com.example.speechmanager.model.Speech;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SpeechMapper {
    SpeechDTO toDto(Speech speech);
    Speech toEntity(SpeechDTO dto);
}
