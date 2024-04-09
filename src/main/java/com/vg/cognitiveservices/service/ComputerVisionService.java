package com.vg.cognitiveservices.service;

import com.vg.cognitiveservices.model.ComputerVisionResponse;
import reactor.core.publisher.Mono;

public interface ComputerVisionService {
    Mono<ComputerVisionResponse> save(String imageUrl);
}
