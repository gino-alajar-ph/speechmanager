package com.example.speechmanager.repository;

import com.example.speechmanager.model.Speech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SpeechRepository extends JpaRepository<Speech, Long>, JpaSpecificationExecutor<Speech> {
}
