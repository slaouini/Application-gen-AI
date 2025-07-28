package com.aiproject.genai_app.controller;

import com.aiproject.genai_app.service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AIController {

    @Autowired
    private OpenAIService openAIService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestParam String question, Model model) {
        String response = openAIService.askQuestion(question);
        model.addAttribute("question", question);
        model.addAttribute("response", response);
        return "index";
    }

    @GetMapping("/test-key")
    @ResponseBody
    public String testApiKey() {
        boolean valid = openAIService.testApiKey();
        return valid ? "Clé API valide ✅" : "Clé API invalide ou problème réseau ❌";
    }


}
