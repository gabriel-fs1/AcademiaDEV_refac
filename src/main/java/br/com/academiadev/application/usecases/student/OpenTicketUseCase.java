package br.com.academiadev.application.usecases.student;

import br.com.academiadev.application.repositories.TicketRepository;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.SupportTicket;

public class OpenTicketUseCase {

    private final TicketRepository ticketRepository;

    public OpenTicketUseCase(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void execute(Student student, String title, String message) {
        SupportTicket ticket = new SupportTicket(title, message, student);
        ticketRepository.add(ticket);
    }

    public void execute(String email, String title, String message) {
        SupportTicket ticket = new SupportTicket(title, message, email);
        ticketRepository.add(ticket);
    }
}
