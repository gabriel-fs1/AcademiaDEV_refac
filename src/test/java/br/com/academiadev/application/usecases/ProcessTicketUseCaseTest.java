package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.TicketQueue;
import br.com.academiadev.domain.entities.Admin;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.SupportTicket;
import br.com.academiadev.domain.entities.BasicPlan;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessTicketUseCaseTest {

    @Mock
    private TicketQueue ticketQueue;

    @InjectMocks
    private ProcessTicketUseCase useCase;

    @Test
    @DisplayName("Admin deve processar o próximo ticket da fila")
    void shouldProcessNextTicket() {

        Admin admin = new Admin("Super Admin", "admin@email.com");
        Student student = new Student("Aluno", "aluno@email.com", new BasicPlan());
        SupportTicket pendingTicket = new SupportTicket("Help", "Login error", student);

        when(ticketQueue.isEmpty()).thenReturn(false);
        when(ticketQueue.next()).thenReturn(pendingTicket);

        SupportTicket processedTicket = useCase.execute(admin);

        assertNotNull(processedTicket);
        assertNotNull(processedTicket.getProcessedAt(), "O ticket deve ter data de processamento");
        verify(ticketQueue, times(1)).next();
    }

    @Test
    @DisplayName("Deve retornar null/erro se a fila estiver vazia")
    void shouldDoNothingIfQueueIsEmpty() {

        Admin admin = new Admin("Super Admin", "admin@email.com");
        when(ticketQueue.isEmpty()).thenReturn(true);

        assertThrows(RuntimeException.class, () -> useCase.execute(admin));

        verify(ticketQueue, never()).next();
    }
}
