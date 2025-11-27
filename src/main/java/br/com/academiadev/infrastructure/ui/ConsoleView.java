package br.com.academiadev.infrastructure.ui;

import java.util.Scanner;

public class ConsoleView {
    private final Scanner scanner = new Scanner(System.in);

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String error) {
        System.err.println("ERROR: " + error);
 
        try { Thread.sleep(500); } catch (InterruptedException e) {}
    }

    public String askInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().trim();
    }

    public void showTitle(String title) {
        System.out.println("\n=== " + title.toUpperCase() + " ===");
    }
}
