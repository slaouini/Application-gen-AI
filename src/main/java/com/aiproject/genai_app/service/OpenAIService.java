package com.aiproject.genai_app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);
    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.model}")
    private String model;


    public OpenAIService(@Value("${openai.api.key}") String apiKey) {
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .clientConnector(
                        new ReactorClientHttpConnector(
                                HttpClient.create()
                                        .responseTimeout(Duration.ofSeconds(10))
                        )
                )
                .build();
    }


    public String askQuestion(String prompt) {
        try {
            Map<String, Object> requestBody = Map.of(
                    "model", model,
                    "messages", List.of(
                            Map.of("role", "user", "content", prompt)
                    )
            );

            Map<String, Object> response = webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                    .block();

            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");


        } catch (WebClientResponseException.TooManyRequests e) {
            return "Erreur : Trop de requêtes. Merci de patienter avant de réessayer.";
        } catch (WebClientResponseException.Unauthorized e) {
            return "Erreur : Clé API invalide ou non autorisée.";
        } catch (WebClientResponseException e) {
            return "Erreur HTTP : " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return "Erreur inattendue : " + e.getMessage();
        }

    }


    public boolean testApiKey() {
        try {
            Map<String, Object> requestBody = Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", List.of(
                            Map.of("role", "user", "content", "Say hello")
                    )
            );

            webClient.post()
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block(Duration.ofSeconds(5));

            return true;
        } catch (org.springframework.web.reactive.function.client.WebClientResponseException.Unauthorized e) {
            return false; // Clé invalide
        } catch (Exception e) {
            return false; // Autre erreur (timeout, réseau, etc.)
        }
    }

}
