package com.afronet.controller;

import com.afronet.dto.ChatGPTRequest;
import com.afronet.dto.ChatGPTResponse;
import com.afronet.dto.Message;
import com.theokanning.openai.completion.chat.*;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@Slf4j
@RequestMapping("/api/v1")
public class ChatGPTController {
    private final RestTemplate restTemplate;

    public ChatGPTController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Value("${OPEN_AI_MODEL}")
    private String model;
    @Value("${OPEN_AI_KEY}")
    private String apiKey;
    @Value(("${OPEN_AI_TEMPERATURE}"))
    private double temperature;
    @Value(("${OPEN_AI_MAX_TOKENS}"))
    private Integer maxToken;
    @Value("${OPEN_AI_MAX_COMPLETION}")
    private Integer completion;
    @Value(("${OPEN_AI_URL}"))
    private String aiUrl;


// Get API Call that uses com.theokanning.openai-gpt3-java dependency

    @GetMapping("/chat")
    public String chatResponse(@RequestParam("prompt") String prompt){
        OpenAiService service = new OpenAiService(apiKey);
        List<ChatMessage> messages = new ArrayList<>();


        ChatMessage message = new ChatMessage(ChatMessageRole.USER.value(),prompt);
        messages.add(message);
        ChatCompletionRequest completionRequest = ChatCompletionRequest.
                builder()
                .messages(messages)
                .model(model)
                .n(1)
                .temperature(temperature)
                .maxTokens(maxToken)
                .build();
        List<ChatCompletionChoice> choices = service.createChatCompletion(completionRequest).getChoices();
        String returnString = " ";
        for (ChatCompletionChoice choice: choices){
            returnString = choice.getMessage().getContent() + System.lineSeparator();
        }
        if(returnString.isEmpty() || returnString.isBlank()){
            return "Unable to provide a response";
        }
        return returnString;

    }

    @PostMapping("/chatResponse")
    public com.afronet.dto.ChatGPTResponse chat(@RequestBody String prompt) {
        List<Message> messages = new ArrayList<>();
        Message message = new Message(ChatMessageRole.USER.value(), prompt);
        messages.add(message);

        ChatGPTRequest request = new ChatGPTRequest(model, messages, completion, temperature, maxToken);

        return restTemplate.postForObject(aiUrl, request, ChatGPTResponse.class);
    }

}
