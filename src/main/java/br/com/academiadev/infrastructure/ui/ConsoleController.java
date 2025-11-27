package br.com.academiadev.infrastructure.ui;

import br.com.academiadev.application.repositories.CourseRepository;
import br.com.academiadev.application.repositories.EnrollmentRepository;
import br.com.academiadev.application.repositories.TicketQueue;
import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.application.usecases.EnrollStudentUseCase;
import br.com.academiadev.application.usecases.ManageCourseUseCase;
import br.com.academiadev.application.usecases.ProcessTicketUseCase;
import br.com.academiadev.domain.entities.*;
import br.com.academiadev.infrastructure.utils.GenericCsvExporter;

import java.util.Arrays;
import java.util.List;

public class ConsoleController {

    private final ConsoleView view;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    private final EnrollStudentUseCase enrollStudentUseCase;
    private final ManageCourseUseCase manageCourseUseCase;
    private final ProcessTicketUseCase processTicketUseCase;
    private final TicketQueue ticketQueue;

    private User loggedUser;

    public ConsoleController(ConsoleView view,
                             UserRepository userRepository,
                             CourseRepository courseRepository,
                             EnrollmentRepository enrollmentRepository,
                             TicketQueue ticketQueue) {
        this.view = view;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.ticketQueue = ticketQueue;

        this.enrollStudentUseCase = new EnrollStudentUseCase(userRepository, courseRepository, enrollmentRepository);
        this.manageCourseUseCase = new ManageCourseUseCase(courseRepository);
        this.processTicketUseCase = new ProcessTicketUseCase(ticketQueue);
    }

    public void start() {
        view.showTitle("Bem-vindo à AcademiaDev");
        while (true) {
            if (loggedUser == null) {
                login();
            } else {
                if (loggedUser instanceof Admin) {
                    showAdminMenu();
                } else {
                    showStudentMenu();
                }
            }
        }
    }

    private void login() {
        String email = view.askInput("Digite seu e-mail para entrar (ou 'exit' para sair)");
        if (email.equalsIgnoreCase("exit")) System.exit(0);

        userRepository.findByEmail(email).ifPresentOrElse(
            user -> {
                this.loggedUser = user;
                view.showMessage("Login realizado com sucesso! Olá, " + user.getName());
            },
            () -> view.showError("Usuário não encontrado. Tente novamente.")
        );
    }

    private void logout() {
        this.loggedUser = null;
        view.showMessage("Você saiu do sistema.");
    }

    private void showAdminMenu() {
        view.showTitle("Menu Admin");
        view.showMessage("1. Processar Ticket de Suporte");
        view.showMessage("2. Alternar Status de Curso");
        view.showMessage("3. Exportar Cursos para CSV");
        view.showMessage("4. Gerar Relatórios (Em Breve)");
        view.showMessage("0. Logout");

        String option = view.askInput("Escolha");

        try {
            switch (option) {
                case "1" -> processTicket();
                case "2" -> toggleCourseStatus();
                case "3" -> exportCoursesCsv();
                case "4" -> view.showMessage("Implementaremos o UseCase de Relatórios no próximo passo!");
                case "0" -> logout();
                default -> view.showError("Opção inválida");
            }
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    private void showStudentMenu() {
        view.showTitle("Menu Aluno");
        view.showMessage("1. Matricular em Curso");
        view.showMessage("2. Meus Cursos (Ver Progresso)");
        view.showMessage("3. Abrir Ticket de Suporte");
        view.showMessage("0. Logout");

        String option = view.askInput("Escolha");

        try {
            switch (option) {
                case "1" -> enrollStudent();
                case "2" -> showMyEnrollments();
                case "3" -> openSupportTicket();
                case "0" -> logout();
                default -> view.showError("Opção inválida");
            }
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    // --- AÇÕES ---

    private void processTicket() {
        Admin admin = (Admin) loggedUser;
        try {
            SupportTicket ticket = processTicketUseCase.execute(admin);
            view.showMessage("Ticket Processado com Sucesso!");
            view.showMessage("Título: " + ticket.getTitle());
            view.showMessage("Aberto por: " + ticket.getOpener().getEmail());
        } catch (RuntimeException e) {
            view.showError(e.getMessage());
        }
    }

    private void toggleCourseStatus() {
        String title = view.askInput("Digite o título exato do curso");
        manageCourseUseCase.toggleCourseStatus(title);
        view.showMessage("Status do curso alterado com sucesso.");
    }

    private void enrollStudent() {
        String courseTitle = view.askInput("Digite o título do curso");
        enrollStudentUseCase.execute(loggedUser.getEmail(), courseTitle);
        view.showMessage("Matrícula realizada com sucesso!");
    }

    private void showMyEnrollments() {
        Student student = (Student) loggedUser;
        List<Enrollment> enrollments = student.getAllEnrollments();

        if (enrollments.isEmpty()) {
            view.showMessage("Você não está matriculado em nenhum curso.");
            return;
        }

        view.showMessage("--- Seus Cursos ---");
        for (Enrollment e : enrollments) {
            String status = e.getCourse().isActive() ? "Ativo" : "Pausado";
            view.showMessage(String.format("- %s (Progresso: %d%%) [%s]",
                e.getCourse().getTitle(), e.getProgressPercent(), status));
        }
    }

    private void openSupportTicket() {
        String title = view.askInput("Título do problema");
        String msg = view.askInput("Descrição");

        SupportTicket ticket = new SupportTicket(title, msg, loggedUser);
        ticketQueue.add(ticket);

        view.showMessage("Ticket aberto! Aguarde atendimento.");
    }

    private void exportCoursesCsv() {
        List<Course> courses = courseRepository.findAll();

        String colsInput = view.askInput("Quais colunas exportar? (separe por vírgula, ex: title,instructorName,status)");
        List<String> columns = Arrays.asList(colsInput.split(","));

        String csv = GenericCsvExporter.exportToCsv(courses, columns);
        view.showMessage("\n--- CSV GERADO ---\n");
        System.out.println(csv);
    }
}
