package br.com.academiadev.domain.entities;

import java.time.LocalDateTime;

public class SupportTicket {
    private String title;
    private String message;
    private User opener; 
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;

    public SupportTicket(String title, String message, User opener) {
        this.title = title;
        this.message = message;
        this.opener = opener;
        this.createdAt = LocalDateTime.now();
    }

    public void markAsProcessed() {
        this.processedAt = LocalDateTime.now();
    }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public String getTitle() { return title; }

    public User getOpener() {
        return opener;
    }
}
