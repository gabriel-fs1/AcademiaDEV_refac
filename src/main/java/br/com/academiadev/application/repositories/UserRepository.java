package br.com.academiadev.application.repositories;

import br.com.academiadev.domain.entities.User;
import java.util.Optional;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(String email);
}
