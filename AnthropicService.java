package com.iachat.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iachat.model.ChatRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

/**
 * Serviço que se comunica com a API da Anthropic (Claude).
 * Usa o java.net.http.HttpClient nativo do Java 11+.
 */
@Service
public class AnthropicService {

    @Value("${anthropic.api.key}")
    private String apiKey;

    @Value("${anthropic.model}")
    private String model;

    @Value("${anthropic.max-tokens}")
    private int maxTokens;

    private static final String API_URL = "https://api.anthropic.com/v1/messages";
    private static final String API_VERSION = "2023-06-01";

    private final HttpClient httpClient;
    private final ObjectMapper mapper;

    public AnthropicService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();
        this.mapper = new ObjectMapper();
    }

    /**
     * Envia mensagens para a API Claude e retorna o texto de resposta.
     *
     * @param userMessage  última pergunta do usuário
     * @param history      histórico anterior da conversa
     * @return texto de resposta da IA
     */
    public String chat(String userMessage, List<ChatRequest.HistoryMessage> history) throws Exception {

        // ── Monta o corpo da requisição em JSON ───────────────
        ObjectNode body = mapper.createObjectNode();
        body.put("model", model);
        body.put("max_tokens", maxTokens);

        // System prompt – define o comportamento da IA
        body.put("system",
            "Você é um assistente de IA amigável e inteligente chamado JavaBot. " +
            "Responda SEMPRE em português do Brasil, de forma clara, simpática e objetiva. " +
            "Para perguntas simples, dê respostas diretas. " +
            "Quando usar código, formate com blocos de código usando crases triplas. " +
            "Evite respostas muito longas para perguntas simples."
        );

        // ── Histórico + nova mensagem ─────────────────────────
        ArrayNode messages = mapper.createArrayNode();

        if (history != null) {
            for (ChatRequest.HistoryMessage h : history) {
                ObjectNode msg = mapper.createObjectNode();
                msg.put("role", h.getRole());
                msg.put("content", h.getContent());
                messages.add(msg);
            }
        }

        // Adiciona a nova pergunta do usuário
        ObjectNode userMsg = mapper.createObjectNode();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        messages.add(userMsg);

        body.set("messages", messages);

        // ── Faz a requisição HTTP ─────────────────────────────
        String requestBody = mapper.writeValueAsString(body);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/json")
                .header("x-api-key", apiKey)
                .header("anthropic-version", API_VERSION)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request,
                HttpResponse.BodyHandlers.ofString());

        // ── Trata a resposta ──────────────────────────────────
        if (response.statusCode() != 200) {
            JsonNode errorNode = mapper.readTree(response.body());
            String errorMsg = errorNode.path("error").path("message").asText("Erro desconhecido");
            throw new RuntimeException("API retornou " + response.statusCode() + ": " + errorMsg);
        }

        JsonNode responseJson = mapper.readTree(response.body());
        JsonNode contentArray = responseJson.path("content");

        StringBuilder sb = new StringBuilder();
        for (JsonNode block : contentArray) {
            if ("text".equals(block.path("type").asText())) {
                sb.append(block.path("text").asText());
            }
        }

        return sb.toString().trim();
    }
}
