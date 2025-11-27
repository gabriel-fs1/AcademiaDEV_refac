package br.com.academiadev.application.repositories;

import java.util.List;
import java.util.Optional;

import br.com.academiadev.domain.entities.Student;

public interface StudentRepository {
    List<Student> findAll();
    Optional<Student> findByEmail(String email);
    void save(Student student);
}
