package com.vg.cognitiveservices.controller;

import com.vg.cognitiveservices.dto.ImageAnalysisDTO;
import com.vg.cognitiveservices.model.ComputerVisionResponse;
import com.vg.cognitiveservices.service.ComputerVisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/images")
public class ImageAnalysisController {

    @Autowired
    private ComputerVisionService visionService;

    @PostMapping("/analyze")
    public Mono<ComputerVisionResponse> analyzeImage(@RequestBody ImageAnalysisDTO request) {
        return visionService.save(request.getImageUrl());
    }
}
