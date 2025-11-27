package br.com.academiadev.domain.entities;

public class Admin extends User {
    public Admin(String name, String email) {
        super(name, email);
    }

    public void processTicket(SupportTicket ticket) {
        ticket.markAsProcessed();
    }
}
