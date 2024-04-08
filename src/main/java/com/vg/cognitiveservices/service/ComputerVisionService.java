package com.vg.cognitiveservices.service;

import com.vg.cognitiveservices.model.Description;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public interface ComputerVisionService {

    Flux<Description> getAll();

    Mono<Description> save(Description description) throws IOException;
}
