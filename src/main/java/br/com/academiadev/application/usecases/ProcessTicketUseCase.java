package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.TicketQueue;
import br.com.academiadev.domain.entities.Admin;
import br.com.academiadev.domain.entities.SupportTicket;

public class ProcessTicketUseCase {

    private final TicketQueue ticketQueue;

    public ProcessTicketUseCase(TicketQueue ticketQueue) {
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
