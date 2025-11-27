package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.SupportTicket;
import br.com.academiadev.domain.entities.BasicPlan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTicketQueueTest {

    private InMemoryTicketQueue queue;

    @BeforeEach
    void setUp() {
        queue = new InMemoryTicketQueue();
    }

    @Test
    @DisplayName("Deve respeitar a ordem FIFO (First-In, First-Out)")
    void shouldFollowFifoOrder() {
        Student student = new Student("A", "a@a.com", new BasicPlan());

        SupportTicket ticket1 = new SupportTicket("Primeiro", "Msg 1", student);
        SupportTicket ticket2 = new SupportTicket("Segundo", "Msg 2", student);

        queue.add(ticket1);
        queue.add(ticket2);

        SupportTicket next = queue.next();
        assertEquals("Primeiro", next.getTitle());

        next = queue.next();
        assertEquals("Segundo", next.getTitle());

        assertTrue(queue.isEmpty());
    }
}
