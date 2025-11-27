package br.com.academiadev.domain.entities;

import java.time.LocalDateTime;

public class SupportTicket {
    private String title;
    private String message;
    private User opener;
    private String contactEmail;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;

    public SupportTicket(String title, String message, User opener) {
        this.title = title;
        this.message = message;
        this.opener = opener;
        this.contactEmail = opener.getEmail();
        this.createdAt = LocalDateTime.now();
    }

    public SupportTicket(String title, String message, String contactEmail) {
        this.title = title;
        this.message = message;
        this.opener = null;
        this.contactEmail = contactEmail;
        this.createdAt = LocalDateTime.now();
    }

    public void markAsProcessed() {
        this.processedAt = LocalDateTime.now();
    }

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public User getOpener() {
        return opener;
    }

    public String getOpenerName() {
        return opener != null ? opener.getName() : "Anônimo (" + contactEmail + ")";
    }
}
