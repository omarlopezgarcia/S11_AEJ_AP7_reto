package pe.edu.vallegrande.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.app.model.GeminiTopicResponse;
import pe.edu.vallegrande.app.service.GeminiTopicService;

@RestController
@RequestMapping("/geminitopic")
public class GeminiTopicRest {

    @Autowired
    private GeminiTopicService geminiService;

    @PostMapping("/ask")
    public String askQuestion(@RequestBody GeminiTopicResponse request) {
        try {
            return geminiService.askQuestion(request.getTopic(), request.getQuestion());
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
