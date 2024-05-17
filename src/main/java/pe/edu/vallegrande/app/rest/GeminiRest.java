package pe.edu.vallegrande.app.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.vallegrande.app.model.GeminiImageAnalysis;
import pe.edu.vallegrande.app.service.impl.GeminiAnalysisServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping("/gemini")
public class GeminiRest {

    private final GeminiAnalysisServiceImpl geminiAnalysisService;

    public GeminiRest(GeminiAnalysisServiceImpl geminiAnalysisService) {
        this.geminiAnalysisService = geminiAnalysisService;
    }

    @PostMapping("/upload/image")
    public Mono<GeminiImageAnalysis> uploadFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("language") String language) {
        return geminiAnalysisService.processImage(file, language);
    }

    @GetMapping
    public Flux<GeminiImageAnalysis> getAll() {
        return geminiAnalysisService.getAll();
    }
}
