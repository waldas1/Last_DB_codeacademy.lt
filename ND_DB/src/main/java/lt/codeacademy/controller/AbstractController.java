package lt.codeacademy.controller;

import lt.codeacademy.entity.Test;
import lt.codeacademy.entity.User;
import lt.codeacademy.service.TestService;
import lt.codeacademy.service.UserService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

abstract class AbstractController {
    private final Scanner scanner;
    private final UserService userService;
    private final TestService testService;

    AbstractController() {
        this.scanner = new Scanner(System.in);
        this.userService = new UserService();
        this.testService = new TestService();
    }

    void registration() {
        LogInController logInController = new LogInController();
        try {
            System.out.print("Iveskite savo varda: ");
            String newUserName = scanner.nextLine();

            System.out.print("iveskite pavarde: ");
            String newUserSurname = scanner.nextLine();

            String newUserUsername = logInController.checkUsername();

            System.out.print("Iveskite jusu slaptazodi: ");
            String newUserPassword = scanner.nextLine();

            String role = logInController.userGetRole();

            System.out.print("Iveskite savo gimimo data, formatu(yyyy-MM-dd): ");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(scanner.nextLine(), formatter);

            User newUser = new User(newUserName, newUserUsername, newUserPassword, newUserSurname, role, date);
            userService.createNewUser(newUser);
            System.out.print("Registracija sekminga!\n");

        } catch (InputMismatchException e) {
            System.out.println("Bloga ivestis!");
        } catch (Exception e) {
            System.out.println("Kazkas atsitiko!");
        }

    }

    Long getCorrectNumber() {
        while (true) {
            try {
                return Long.valueOf(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Neteisinga ivestis! Pabandykite dar karta");
            }
        }
    }

    Test getTestByTestId() {
        List<Test> tests = testService.getTests();
        for (Test test : tests) {
            System.out.printf("%s %s %s\n", test.getId(), test.getTitle(), test.getType());
        }
        System.out.print("Iveskite egzamino id: ");
        Test test = getExam(tests);
        return test;
    }

    Test getExam(List<Test> tests) {
        Test test;
        do {
            Long examId = getCorrectNumber();
            test = tests.stream()
                    .filter(t -> t.getId().equals(examId))
                    .findFirst()
                    .orElse(null);

            if (test == null) {
                System.out.print("Pasirinkite egzamina: ");
            }
        } while (test == null);

        return test;
    }
}