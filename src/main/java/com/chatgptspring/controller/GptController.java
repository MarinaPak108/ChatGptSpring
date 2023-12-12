package com.chatgptspring.controller;

import com.chatgptspring.dto.GptReply;
import com.chatgptspring.dto.GptRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class GptController {
    @Qualifier("gpt")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @GetMapping("/ask")
    public String chat(@RequestParam String prompt) {
        GptRequest request = new GptRequest(model, prompt);

        GptReply response = restTemplate.postForObject(apiUrl, request, GptReply.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return response
        return response.getChoices().get(0).getMessage().getContent();
    }
}
