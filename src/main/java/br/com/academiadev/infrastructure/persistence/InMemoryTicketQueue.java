package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.application.repositories.TicketQueue;
import br.com.academiadev.domain.entities.SupportTicket;

import java.util.ArrayDeque;
import java.util.Queue;

public class InMemoryTicketQueue implements TicketQueue {

    private final Queue<SupportTicket> queue = new ArrayDeque<>();

    @Override
    public void add(SupportTicket ticket) {
        queue.offer(ticket);
    }

    @Override
    public SupportTicket next() {
        return queue.poll();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
