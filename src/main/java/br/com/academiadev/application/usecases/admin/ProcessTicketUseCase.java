package br.com.academiadev.application.usecases.admin;

import br.com.academiadev.application.repositories.TicketRepository;
import br.com.academiadev.domain.entities.Admin;
import br.com.academiadev.domain.entities.SupportTicket;

public class ProcessTicketUseCase {

    private final TicketRepository ticketQueue;

    public ProcessTicketUseCase(TicketRepository ticketQueue) {
        this.ticketQueue = ticketQueue;
    }

    public SupportTicket execute(Admin admin) {
        if (ticketQueue.isEmpty()) {
            throw new RuntimeException("No tickets to process.");
        }
        SupportTicket ticket = ticketQueue.next();

        admin.processTicket(ticket);

        return ticket;
    }
}
