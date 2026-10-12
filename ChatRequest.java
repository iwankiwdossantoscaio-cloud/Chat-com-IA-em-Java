package com.iachat.model;

import java.util.List;

// ── Requisição vinda do frontend ──────────────────────────────
public class ChatRequest {

    private String message;
    private List<HistoryMessage> history;

    public ChatRequest() {}

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<HistoryMessage> getHistory() { return history; }
    public void setHistory(List<HistoryMessage> history) { this.history = history; }

    // ── Mensagem do histórico de conversa ─────────────────────
    public static class HistoryMessage {
        private String role;    // "user" ou "assistant"
        private String content;

        public HistoryMessage() {}
        public HistoryMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }
}
