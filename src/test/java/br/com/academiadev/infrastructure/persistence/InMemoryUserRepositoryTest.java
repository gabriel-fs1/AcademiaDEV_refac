package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.domain.entities.BasicPlan;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryTest {

    private InMemoryUserRepository repository;

    @BeforeEach
    void setUp() {
        repository = new InMemoryUserRepository();
    }

    @Test
    @DisplayName("Deve salvar e encontrar usuário por email")
    void shouldSaveAndFindUser() {
        User user = new Student("Gabriel", "gabriel@email.com", new BasicPlan());

        repository.save(user);

        Optional<User> found = repository.findByEmail("gabriel@email.com");
        assertTrue(found.isPresent());
        assertEquals("Gabriel", found.get().getName());
    }
}
