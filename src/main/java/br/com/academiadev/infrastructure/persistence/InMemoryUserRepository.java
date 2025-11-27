package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.domain.entities.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> db = new HashMap<>();

    @Override
    public void save(User user) {
        db.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(db.get(email));
    }
}
