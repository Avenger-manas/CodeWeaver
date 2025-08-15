package com.dolpi.CodeWeaver.Controller;

import com.dolpi.CodeWeaver.Entity.AiClass;
import com.dolpi.CodeWeaver.Entity.User;
import com.dolpi.CodeWeaver.Repository.Userrepository;
import com.dolpi.CodeWeaver.UserService.Userservice;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/createcode")
public class createcode {
    private OllamaChatModel client;

    @Autowired
    private Userservice userservice;

    @Autowired
    Userrepository userrepository;

    public createcode(OllamaChatModel client) {
        this.client = client;
    }

    @PostMapping("/codelogic")
    public Flux<String> codeLogic(@RequestBody AiClass airequest) {
        try {
            // Current authenticated user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            // Build message string
            String s = airequest.getMessage()
                    + "\nLanguage: " + airequest.getLanguage()
                    + "\nPrompt: " + airequest.getPrompt();

            // Call client.stream method (assuming it returns Flux<String>)
            return client.stream(s);

        } catch (Exception e) {
            e.printStackTrace();
            // Return a Flux with error message
            return Flux.just("Error processing request: " + e.getMessage());
        }
    }
}