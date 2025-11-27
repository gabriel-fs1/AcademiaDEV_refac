package br.com.academiadev.application.usecases.student;

import java.util.ArrayList;
import java.util.List;

import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.Student;

public class ListStudentsUseCase {

    private final StudentRepository repository;

    public ListStudentsUseCase(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> execute() {
        List<Student> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }
}
