package com.aiproject.genai_app.controller;

import com.aiproject.genai_app.service.OpenAIService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final OpenAIService openAIService;

    public ChatController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @GetMapping("/ask")
    public String ask(@RequestParam String question) {
        return openAIService.askQuestion(question);
    }
}
