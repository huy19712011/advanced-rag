package org.example.advancedrag.controller;

import lombok.RequiredArgsConstructor;
import org.example.advancedrag.dto.ChatRequest;
import org.example.advancedrag.dto.ChatResponse;
import org.example.advancedrag.service.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {

        return chatService.getResponse(request);
    }

}
