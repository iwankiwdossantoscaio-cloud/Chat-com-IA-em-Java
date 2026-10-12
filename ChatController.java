package com.iachat.controller;

import com.iachat.model.ChatRequest;
import com.iachat.model.ChatResponse;
import com.iachat.service.AnthropicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST que expõe o endpoint /api/chat.
 * O frontend HTML faz POST para este endpoint.
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")   // permite chamadas do frontend
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final AnthropicService anthropicService;

    public ChatController(AnthropicService anthropicService) {
        this.anthropicService = anthropicService;
    }

    /**
     * POST /api/chat
     * Body: { "message": "...", "history": [...] }
     * Return: { "reply": "...", "success": true }
     */
    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest req) {
        log.info("Mensagem recebida: {}", req.getMessage());

        if (req.getMessage() == null || req.getMessage().isBlank()) {
            return ResponseEntity.badRequest()
                    .body(ChatResponse.fail("Mensagem não pode estar vazia."));
        }

        try {
            String reply = anthropicService.chat(req.getMessage(), req.getHistory());
            log.info("Resposta gerada com {} caracteres", reply.length());
            return ResponseEntity.ok(ChatResponse.ok(reply));

        } catch (Exception e) {
            log.error("Erro ao chamar a API: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(ChatResponse.fail("Erro: " + e.getMessage()));
        }
    }

    /**
     * GET /api/health
     * Verifica se o servidor está no ar.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("{\"status\":\"ok\",\"message\":\"IA Chat rodando!\"}");
    }
}
