package com.iachat.model;

// ── Resposta enviada ao frontend ──────────────────────────────
public class ChatResponse {

    private String reply;
    private boolean success;
    private String error;

    public ChatResponse() {}

    // Resposta de sucesso
    public static ChatResponse ok(String reply) {
        ChatResponse r = new ChatResponse();
        r.reply = reply;
        r.success = true;
        return r;
    }

    // Resposta de erro
    public static ChatResponse fail(String error) {
        ChatResponse r = new ChatResponse();
        r.error = error;
        r.success = false;
        return r;
    }

    public String getReply()   { return reply; }
    public boolean isSuccess() { return success; }
    public String getError()   { return error; }
}
