package br.com.academiadev.application.repositories;

import br.com.academiadev.domain.entities.SupportTicket;

public interface TicketQueue {
    void add(SupportTicket ticket); 
    SupportTicket next();
    boolean isEmpty();
}
