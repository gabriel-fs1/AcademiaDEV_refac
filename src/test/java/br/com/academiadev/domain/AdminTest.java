package br.com.academiadev.domain;

import br.com.academiadev.domain.entities.Admin;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.SupportTicket;
import br.com.academiadev.domain.entities.BasicPlan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {

    @Test
    @DisplayName("Admin deve ser capaz de processar um ticket de suporte")
    void shouldProcessTicket() {
        Admin admin = new Admin("Super Admin", "admin@academia.dev");
        Student student = new Student("João", "joao@email.com", new BasicPlan());
        SupportTicket ticket = new SupportTicket("Problema", "Não acesso o curso", student);

        assertNull(ticket.getProcessedAt(), "Ticket novo não deve ter data de processamento");

        admin.processTicket(ticket);

        assertNotNull(ticket.getProcessedAt(), "Ticket processado deve ter data preenchida");
    }
}
