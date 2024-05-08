package pe.edu.vallegrande.app.rest;

import pe.edu.vallegrande.app.dto.ImageAnalysisDTO;
import pe.edu.vallegrande.app.model.ComputerVisionResponse;
import pe.edu.vallegrande.app.service.ComputerVisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:4321")
@RequestMapping("/api/images")
public class ImageAnalysisController {

    @Autowired
    private ComputerVisionService visionService;

    @PostMapping("/analyze")
    public Mono<ComputerVisionResponse> analyzeImage(@RequestBody ImageAnalysisDTO request) {

        return visionService.save(request.getImageUrl());
    }
    @GetMapping
    public Flux<ComputerVisionResponse> getAll(){
        return visionService.getAll();
    }
}
