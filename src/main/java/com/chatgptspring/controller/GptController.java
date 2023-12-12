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
        // create a request
        GptRequest request = new GptRequest(model, prompt);

        // call the API
        GptReply response = restTemplate.postForObject(apiUrl, request, GptReply.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return the first response
        return response.getChoices().get(0).getMessage().getContent();
    }
}
