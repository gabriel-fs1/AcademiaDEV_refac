package br.com.academiadev.infrastructure.persistence;

import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryStudentRepository implements StudentRepository {

    private final Map<String, Student> db = new HashMap<>();

    @Override
    public void save(Student student) {
        db.put(student.getEmail(), student);
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        return Optional.ofNullable(db.get(email));
    }

    @Override
    public List<Student> findAll() {
        return new ArrayList<>(db.values());
    }
}
