package com.vg.cognitiveservices.repository;

import com.vg.cognitiveservices.model.ComputerVisionResponse;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ComputerVisionResponseRepository extends ReactiveCrudRepository<ComputerVisionResponse, Long> {
}
