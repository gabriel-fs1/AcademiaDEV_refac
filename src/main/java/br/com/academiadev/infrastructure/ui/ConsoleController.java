package br.com.academiadev.infrastructure.ui;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.com.academiadev.application.repositories.UserRepository;
import br.com.academiadev.application.usecases.admin.GenerateReportsUseCase;
import br.com.academiadev.application.usecases.admin.ProcessTicketUseCase;
import br.com.academiadev.application.usecases.courses.*;
import br.com.academiadev.application.usecases.student.*;
import br.com.academiadev.domain.entities.Admin;
import br.com.academiadev.domain.entities.Student;
import br.com.academiadev.domain.entities.User;
import br.com.academiadev.domain.enums.DifficultyLevel;
import br.com.academiadev.infrastructure.utils.GenericCsvExporter;

public class ConsoleController {

    private final ConsoleView view;
    private final UserRepository userRepository;

    private final ListCourseUseCase listCourseUseCase;
    private final ListStudentsUseCase listStudentsUseCase;
    private final ProcessTicketUseCase processTicketUseCase;
    private final ActivateCourseUseCase activateCourseUseCase;
    private final InactivateCourseUseCase inactivateCourseUseCase;
    private final ChangeStudentPlanUseCase changeStudentPlanUseCase;
    private final GenerateReportsUseCase reportsUseCase;
    private final ListAvailableCoursesUseCase listAvailableCoursesUseCase;
    private final ListEnrollStudentUseCase listEnrollStudentUseCase;
    private final EnrollStudentUseCase enrollStudentUseCase;
    private final OpenTicketUseCase openTicketUseCase;
    private final UpdateProgressUseCase updateProgressUseCase;
    private final CancelEnrollmentUseCase cancelEnrollmentUseCase;
    private final SearchCourseTitleUseCase searchCourseTitleUseCase;
    private final SearchStudentUseCase searchStudentUseCase;

    private User loggedUser;

    public ConsoleController(UserRepository userRepository, ListCourseUseCase listCourseUseCase,
            ListStudentsUseCase listStudentsUseCase, ProcessTicketUseCase processTicketUseCase,
            ActivateCourseUseCase activateCourseUseCase, InactivateCourseUseCase inactivateCourseUseCase,
            ChangeStudentPlanUseCase changeStudentPlanUseCase, GenerateReportsUseCase reportsUseCase,
            ListAvailableCoursesUseCase listAvailableCoursesUseCase, ListEnrollStudentUseCase listEnrollStudentUseCase,
            EnrollStudentUseCase enrollStudentUseCase, OpenTicketUseCase openTicketUseCase,
            UpdateProgressUseCase updateProgressUseCase, CancelEnrollmentUseCase cancelEnrollmentUseCase,
            SearchCourseTitleUseCase searchCourseTitleUseCase, SearchStudentUseCase searchStudentUseCase) {

        this.view = new ConsoleView();
        this.userRepository = userRepository;
        this.listCourseUseCase = listCourseUseCase;
        this.listStudentsUseCase = listStudentsUseCase;
        this.processTicketUseCase = processTicketUseCase;
        this.activateCourseUseCase = activateCourseUseCase;
        this.inactivateCourseUseCase = inactivateCourseUseCase;
        this.changeStudentPlanUseCase = changeStudentPlanUseCase;
        this.reportsUseCase = reportsUseCase;
        this.listAvailableCoursesUseCase = listAvailableCoursesUseCase;
        this.listEnrollStudentUseCase = listEnrollStudentUseCase;
        this.enrollStudentUseCase = enrollStudentUseCase;
        this.openTicketUseCase = openTicketUseCase;
        this.updateProgressUseCase = updateProgressUseCase;
        this.cancelEnrollmentUseCase = cancelEnrollmentUseCase;
        this.searchCourseTitleUseCase = searchCourseTitleUseCase;
        this.searchStudentUseCase = searchStudentUseCase;
    }

    public void start() {
        view.showWelcome();
        boolean running = true;

        while (running) {
            if (loggedUser == null) {
                running = handlePublicMenu();
            } else if (loggedUser instanceof Admin) {
                handleAdminMenu();
            } else if (loggedUser instanceof Student) {
                handleStudentMenu();
            }
        }
        view.showMessage("Sistema encerrado.");
    }


    private boolean handlePublicMenu() {
        view.showLoginMenu();
        String op = view.askInput("Opção");

        switch (op) {
            case "1" -> performLogin();
            case "2" -> handleOpenAnonymousTicket();
            case "0" -> { return false; }
            default -> view.showError("Opção inválida");
        }
        return true;
    }

    private void performLogin() {
        String email = view.askInput("Digite seu e-mail");
        userRepository.findByEmail(email).ifPresentOrElse(
                user -> {
                    this.loggedUser = user;
                    view.showMessage("Bem-vindo(a), " + user.getName() + "!");
                },
                () -> view.showError("Usuário não encontrado!"));
    }

    private void handleOpenAnonymousTicket() {
        view.showMessage("--- Abertura de Chamado (Sem Login) ---");
        String email = view.askInput("Seu e-mail para contato");
        String title = view.askInput("Assunto");
        String msg = view.askInput("Descrição do problema");

        try {
            openTicketUseCase.execute(email, title, msg);
            view.showMessage("Ticket aberto com sucesso! Nossa equipe entrará em contato pelo e-mail: " + email);
        } catch (Exception e) {
            view.showError("Erro ao abrir ticket: " + e.getMessage());
        }
    }


    private void handleAdminMenu() {
        view.showAdminMenu(loggedUser.getName());
        String op = view.askInput("Opção");

        switch (op) {
            case "1" -> handleListAllCourses();
            case "2" -> handleExportCsv();
            case "3" -> handleProcessTicket();
            case "4" -> handleCourseStatusToggle();
            case "5" -> handleChangeStudentPlan();
            case "6" -> handleReportsMenu();
            case "0" -> logout();
            default -> view.showError("Opção inválida");
        }
    }

    private void handleListAllCourses() {
        var courses = listCourseUseCase.execute();
        view.showCoursesWithHeader("TODOS OS CURSOS", courses);
    }

    private void handleExportCsv() {
        String entityOp = view.askExportEntity();
        List<?> dataToExport = Collections.emptyList();

        if (entityOp.equals("1")) {
            dataToExport = listCourseUseCase.execute();
        } else if (entityOp.equals("2")) {
            dataToExport = listStudentsUseCase.execute();
        } else {
            view.showError("Opção inválida");
            return;
        }

        String input = view.askColumns(entityOp);
        List<String> columns = Arrays.asList(input.split(","));

        String csv = GenericCsvExporter.exportToCsv(dataToExport, columns);

        view.showMessage("\n--- CSV GERADO ---");
        System.out.println(csv);
        view.askInput("Pressione ENTER...");
    }

    private void handleProcessTicket() {
        try {
            var ticket = processTicketUseCase.execute((Admin) loggedUser);
            System.out.println("\n");
            view.showMessage("Ticket Processado com Sucesso!");
            view.showMessage("Aberto por: " + ticket.getOpenerName());
            view.showMessage("Assunto: " + ticket.getTitle());
            view.showMessage("Mensagem: " + ticket.getMessage());

            view.askInput("Pressione ENTER para continuar...");
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    private void handleCourseStatusToggle() {
        String title = view.askInput("Digite o título exato do curso");

        try {
            var course = searchCourseTitleUseCase.execute(title);
            view.showMessage("Status Atual: [" + course.getStatus() + "]");

            String action = view.askInput("Digite [1] para ATIVAR ou [0] para INATIVAR");

            if ("1".equals(action)) {
                activateCourseUseCase.execute(title);
                view.showMessage("Curso ativado com sucesso!");
            } else if ("0".equals(action)) {
                inactivateCourseUseCase.execute(title);
                view.showMessage("Curso inativado com sucesso!");
            } else {
                view.showError("Ação inválida.");
            }
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    private void handleChangeStudentPlan() {
        String email = view.askInput("Email do aluno");

        try {
            var student = searchStudentUseCase.execute(email);
            view.showMessage("Plano Atual: [" + student.getPlan().getTypeName() + "]");

            String planStr = view.askInput("Novo Plano (BASIC ou PREMIUM)").toUpperCase();

            changeStudentPlanUseCase.execute(email, planStr);
            view.showMessage("Plano alterado para " + planStr + "!");

        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    private void handleReportsMenu() {
        view.showReportsMenu();
        String op = view.askInput("Opção");

        switch (op) {
            case "1" -> {
                view.showDifficultySelectionMenu();
                String diffOp = view.askInput("Escolha o Nível");
                DifficultyLevel level = null;
                switch (diffOp) {
                    case "1" -> level = DifficultyLevel.BEGINNER;
                    case "2" -> level = DifficultyLevel.INTERMEDIATE;
                    case "3" -> level = DifficultyLevel.ADVANCED;
                    case "0" -> { return; }
                    default -> { view.showError("Nível inválido!"); return; }
                }
                var courses = reportsUseCase.getCoursesByDifficulty(level);
                view.showCoursesWithHeader("CURSOS NÍVEL " + level, courses);
                view.askInput("Pressione ENTER para voltar...");
            }
            case "2" -> {
                var instructors = reportsUseCase.getActiveInstructors();
                if (instructors.isEmpty()) view.showMessage("Nenhum instrutor ativo.");
                else {
                    view.showMessage("--- INSTRUTORES ATIVOS ---");
                    instructors.forEach(name -> view.showMessage("- " + name));
                }
                view.askInput("Pressione ENTER...");
            }
            case "3" -> {
                var map = reportsUseCase.getStudentsByPlan();
                if (map.isEmpty()) view.showMessage("Nenhum aluno encontrado.");
                else {
                    view.showMessage("--- ALUNOS POR PLANO ---");
                    map.forEach((plan, students) -> {
                        view.showMessage("[" + plan + "]");
                        students.forEach(s -> view.showMessage("  - " + s.getName()));
                    });
                }
                view.askInput("Pressione ENTER...");
            }
            case "4" -> {
                double avg = reportsUseCase.getGeneralProgressAverage();
                var topStudent = reportsUseCase.getTopStudent();
                view.showMessage("--- ESTATÍSTICAS ---");
                view.showMessage(String.format("Média Geral de Progresso: %.2f%%", avg));
                if (topStudent.isPresent()) {
                    view.showMessage("Aluno Destaque: " + topStudent.get().getName());
                } else {
                    view.showMessage("Aluno Destaque: N/A");
                }
                view.askInput("Pressione ENTER...");
            }
            case "0" -> { /* Voltar */ }
            default -> view.showError("Opção inválida");
        }
    }

    private void handleStudentMenu() {
        view.showStudentMenu(loggedUser.getName());
        String op = view.askInput("Opção");

        switch (op) {
            case "1" -> handleListAvailableCourses();
            case "2" -> handleListEnrollments();
            case "3" -> handleEnrollStudent();
            case "4" -> handleOpenStudentTicket();
            case "5" -> handleUpdateProgress();
            case "6" -> handleCancelEnrollment();
            case "0" -> logout();
            default -> view.showError("Opção inválida");
        }
    }

    private void handleListAvailableCourses() {
        var availableCourses = listAvailableCoursesUseCase.execute();
        view.showCoursesWithHeader("CURSOS DISPONÍVEIS", availableCourses);
    }

    private void handleListEnrollments() {
        var enrollments = listEnrollStudentUseCase.execute((Student) loggedUser);
        view.showEnrollments(enrollments);
    }

    private void handleEnrollStudent() {
        String courseTitle = view.askInput("Digite o título exato do curso");
        try {
            enrollStudentUseCase.execute(loggedUser.getEmail(), courseTitle);
            view.showMessage("SUCESSO: Matrícula realizada em '" + courseTitle + "'!");
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    private void handleOpenStudentTicket() {
        String title = view.askInput("Título do problema");
        String message = view.askInput("Descreva detalhadamente");
        try {
            openTicketUseCase.execute((Student) loggedUser, title, message);
            view.showMessage("Ticket enviado com sucesso! Aguarde o atendimento.");
        } catch (Exception e) {
            view.showError("Erro ao abrir ticket: " + e.getMessage());
        }
    }

    private void handleUpdateProgress() {
        var enrollments = listEnrollStudentUseCase.execute((Student) loggedUser);

        if (enrollments.isEmpty()) {
            view.showMessage("Você não possui matrículas ativas.");
            return;
        }

        view.printEnrollmentsList(enrollments);

        String courseTitle = view.askInput("Digite o nome exato do curso (da lista acima)");
        String progressStr = view.askInput("Novo progresso (0-100)");

        try {
            int progress = Integer.parseInt(progressStr);
            updateProgressUseCase.execute((Student) loggedUser, courseTitle, progress);
            view.showMessage("Progresso atualizado para " + progress + "%!");
        } catch (NumberFormatException e) {
            view.showError("Número inválido.");
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    private void handleCancelEnrollment() {
        var enrollments = listEnrollStudentUseCase.execute((Student) loggedUser);

        if (enrollments.isEmpty()) {
            view.showMessage("Você não possui matrículas para cancelar.");
            return;
        }

        view.printEnrollmentsList(enrollments);
        String courseTitle = view.askInput("Digite o nome exato do curso para CANCELAR");
        String confirm = view.askInput("Tem certeza? Digite 'SIM' para confirmar");

        if (confirm.equalsIgnoreCase("SIM")) {
            try {
                cancelEnrollmentUseCase.execute((Student) loggedUser, courseTitle);
                view.showMessage("Matrícula cancelada com sucesso.");
            } catch (Exception e) {
                view.showError(e.getMessage());
            }
        } else {
            view.showMessage("Operação abortada.");
        }
    }

    private void logout() {
        this.loggedUser = null;
        view.showMessage("Logout realizado.");
    }
}
