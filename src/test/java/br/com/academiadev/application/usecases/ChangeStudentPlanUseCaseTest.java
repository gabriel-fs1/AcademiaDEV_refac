package br.com.academiadev.application.usecases;

import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.application.usecases.student.ChangeStudentPlanUseCase;
import br.com.academiadev.domain.entities.BasicPlan;
import br.com.academiadev.domain.entities.PremiumPlan;
import br.com.academiadev.domain.entities.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChangeStudentPlanUseCaseTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private ChangeStudentPlanUseCase useCase;

    @Test
    @DisplayName("Deve alterar o plano do aluno para PREMIUM com sucesso")
    void shouldChangePlanToPremium() {

        String email = "student@test.com";

        Student student = new Student("João", email, new BasicPlan());

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));

        useCase.execute(email, "PREMIUM");

        assertTrue(student.getPlan() instanceof PremiumPlan);

        verify(studentRepository).save(student);
    }

    @Test
    @DisplayName("Deve alterar o plano do aluno para BASIC com sucesso")
    void shouldChangePlanToBasic() {

        String email = "student@test.com";

        Student student = new Student("João", email, new PremiumPlan());

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));

        useCase.execute(email, "BASIC");

        assertTrue(student.getPlan() instanceof BasicPlan);
        verify(studentRepository).save(student);
    }

    @Test
    @DisplayName("Deve lançar erro se o aluno não for encontrado")
    void shouldThrowExceptionWhenStudentNotFound() {

        String email = "ghost@test.com";
        when(studentRepository.findByEmail(email)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            useCase.execute(email, "PREMIUM")
        );

        assertEquals("Aluno não encontrado: " + email, exception.getMessage());
        verify(studentRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar erro se o tipo de plano for inválido")
    void shouldThrowExceptionWhenPlanTypeIsInvalid() {

        String email = "student@test.com";
        Student student = new Student("João", email, new BasicPlan());

        when(studentRepository.findByEmail(email)).thenReturn(Optional.of(student));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            useCase.execute(email, "ULTRA_MEGA_PLAN")
        );

        assertEquals("Tipo de plano inválido. Use BASIC ou PREMIUM.", exception.getMessage());

        verify(studentRepository, never()).save(any());
    }
}
