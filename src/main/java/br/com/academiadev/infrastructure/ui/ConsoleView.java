package br.com.academiadev.infrastructure.ui;

import java.util.List;
import java.util.Scanner;

import br.com.academiadev.domain.entities.Course;
import br.com.academiadev.domain.entities.Enrollment;

public class ConsoleView {

    private final Scanner scanner = new Scanner(System.in);


    public void showWelcome() {
        System.out.println("\n==================================");
        System.out.println("      ACADEMIADEV - SISTEMA       ");
        System.out.println("==================================");
    }

    public String askInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    public void showMessage(String message) {
        System.out.println(">> " + message);
    }

    public void showError(String error) {
        System.err.println("ERRO: " + error);
        try { Thread.sleep(200); } catch (InterruptedException ignored) {}
    }


    public void showLoginMenu() {
        System.out.println("\n--- TELA INICIAL ---");
        System.out.println("1. Entrar (Login)");
        System.out.println("2. Abrir ticket de suporte");
        System.out.println("0. Sair do Sistema");
    }

    public void showAdminMenu(String adminName) {
        System.out.println("\n=== PAINEL ADMIN: " + adminName + " ===");
        System.out.println("1. Listar Cursos");
        System.out.println("2. Exportar CSV");
        System.out.println("3. Atender Ticket de Suporte");
        System.out.println("4. Ativar/Inativar Curso");
        System.out.println("5. Alterar Plano de Aluno");
        System.out.println("6. Relatórios Gerenciais");
        System.out.println("0. Logout");
    }

    public void showStudentMenu(String studentName) {
        System.out.println("\n=== PAINEL ALUNO: " + studentName + " ===");
        System.out.println("1. Listar Cursos Disponíveis");
        System.out.println("2. Minhas Matrículas");
        System.out.println("3. Matricular em um Curso");
        System.out.println("4. Abrir Ticket de Suporte");
        System.out.println("5. Atualizar Progresso");
        System.out.println("6. Cancelar Matrícula");
        System.out.println("0. Logout");
    }

    public void showReportsMenu() {
        System.out.println("\n--- RELATÓRIOS (ADMIN) ---");
        System.out.println("1. Cursos por Nível (Avançado)");
        System.out.println("2. Instrutores Ativos");
        System.out.println("3. Alunos por Plano");
        System.out.println("4. Estatísticas de Progresso");
        System.out.println("0. Voltar");
    }

    public void showCoursesWithHeader(String headerInfo, List<Course> courses) {
        System.out.println("\n--- RESULTADO: " + headerInfo + " ---");
        if (courses.isEmpty()) {
            System.out.println("Nenhum curso encontrado para este nível.");
        } else {
            for (var c : courses) {
                System.out.printf("- %s | Status: %s | Nível: [%s]%n",
                    c.getTitle(),
                    c.getStatus(),
                    c.getDifficulty());
            }
        }
        System.out.println("---------------------------------------");
    }

    public String askExportEntity() {
        System.out.println("\nO que deseja exportar?");
        System.out.println("1. Cursos");
        System.out.println("2. Alunos");
        return askInput("Opção");
    }

    public String askColumns(String entityType) {
        System.out.println("\n--- Seleção de Colunas ---");

        if (entityType.equals("1")) {
            System.out.println("Campos disponíveis: title, description, instructorName, durationInHours, status, difficulty");
        } else if (entityType.equals("2")) {
            System.out.println("Campos disponíveis: name, email (plan e enrollments não exportam direto pois são objetos)");
        }

        System.out.println("Quais colunas deseja exportar? (separe por vírgula)");
        return askInput("Colunas");
    }

    public void showDifficultySelectionMenu() {
        System.out.println("\n--- SELECIONE O NÍVEL DE DIFICULDADE ---");
        System.out.println("1. BEGINNER (Iniciante)");
        System.out.println("2. INTERMEDIATE (Intermediário)");
        System.out.println("3. ADVANCED (Avançado)");
        System.out.println("0. Voltar");
    }

    public void showEnrollments(List<Enrollment> enrollments) {
        System.out.println("\n--- MINHAS MATRÍCULAS ---");
        if (enrollments.isEmpty()) {
            System.out.println("Você não está matriculado em nenhum curso.");
        } else {
            for (var e : enrollments) {
                System.out.printf("- %s | Progresso: %d%%%n",
                    e.getCourse().getTitle(),
                    e.getProgressPercent());
            }
        }
        System.out.println("\nPressione ENTER para voltar...");
        scanner.nextLine();
    }

    public void printEnrollmentsList(List<Enrollment> enrollments) {
        System.out.println("\n--- LISTA DE MATRÍCULAS ---");
        for (var e : enrollments) {
            System.out.printf("- %s | Progresso: %d%%%n",
                e.getCourse().getTitle(),
                e.getProgressPercent());
        }
        System.out.println("---------------------------");
    }
}
