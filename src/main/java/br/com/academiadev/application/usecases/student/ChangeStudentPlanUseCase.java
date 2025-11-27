package br.com.academiadev.application.usecases.student;

import br.com.academiadev.application.repositories.StudentRepository;
import br.com.academiadev.domain.entities.BasicPlan;
import br.com.academiadev.domain.entities.PremiumPlan;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.SubscriptionPlan;

public class ChangeStudentPlanUseCase {
    private final StudentRepository studentRepository;

    public ChangeStudentPlanUseCase(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void execute(String studentEmail, String newPlanType) {
        Student student = studentRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado: " + studentEmail));

        SubscriptionPlan plan;
        if (newPlanType.equalsIgnoreCase("BASIC")) {
            plan = new BasicPlan();
        } else if (newPlanType.equalsIgnoreCase("PREMIUM")) {
            plan = new PremiumPlan();
        } else {
            throw new RuntimeException("Tipo de plano inválido. Use BASIC ou PREMIUM.");
        }
        student.setPlan(plan);
        studentRepository.save(student);
    }
}
