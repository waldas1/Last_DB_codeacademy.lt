package lt.codeacademy.controller;

import lt.codeacademy.Enum.Role;
import lt.codeacademy.entity.User;
import lt.codeacademy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class LogInController extends AbstractController {
    private final Scanner scanner;
    private final UserService userService;
    private List<User> users;
    private static final Logger LOG = LogManager.getLogger(LogInController.class);

    public LogInController() {
        this.scanner = new Scanner(System.in);
        this.userService = new UserService();
        this.users = new ArrayList<>();
    }

    public void menuAction(Scanner scanner) {
        LOG.info("Iskviestas pagrindinis meniu");
        String action;
        do {
            menu();
            action = scanner.nextLine();
            switch (action) {
                case "1" -> logIn(scanner);
                case "2" -> registration();
                case "3" -> System.out.println("Exiting!");
                default -> System.out.println("Wrong input!");
            }
        } while (!action.equals("3"));
    }

    private void menu() {
        System.out.println("""
                1 - Log In
                2 - Registration
                3 - Exit
                """);
    }

    private void logIn(Scanner scanner) {

        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            users = userService.getUsers();

            User ifCorrect = users.stream()
                    .filter(i -> i.getUsername().equals(username))
                    .filter(i -> i.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);

            if (ifCorrect == null) {
                System.out.println("Bloga ivestis arba tokio vartotojo neegzistuoja!");
            }
            checkRole(username, password);

        } catch (InputMismatchException e) {
            System.out.println("Blogas username arba password!");
        }
    }


    private void checkRole(String username, String password) {

        StudentController studentController = new StudentController();
        TeacherCotroller teacherCotroller = new TeacherCotroller();
        AdminCotroller adminCotroller = new AdminCotroller();

        List<User> users = userService.getUsers();
        for (User checkUser : users) {
            if (username.equals(checkUser.getUsername()) && password.equals(checkUser.getPassword())) {
                if (checkUser.getRole().equals(Role.STUDENT.getRoleName())) {
                    LOG.info("Prisijunge studentas {}", checkUser);
                    studentController.studentMenuAction(checkUser);
                } else if (checkUser.getRole().equals(Role.TEACHER.getRoleName())) {
                    LOG.info("Prisijunge destytojas {}", checkUser);
                    teacherCotroller.teacherMenuAction();
                } else if (checkUser.getRole().equals(Role.ADMIN.getRoleName())) {
                    LOG.info("Prisijunge adminas {}", checkUser);
                    adminCotroller.adminMenuAction();

                }
            }
        }
    }

    public String checkUsername() {

        while (true) {
            System.out.print("Iveskite username: ");
            String username = scanner.nextLine();

            users = userService.getUsers();

            User checkUsername = users.stream()
                    .filter(u -> u.getUsername().equals(username))
                    .findFirst()
                    .orElse(null);

            if (checkUsername != null) {
                System.out.println("Toks vartotojas jau egzistuoja!");
                continue;
            }

            return username;
        }
    }

    public String userGetRole() {
        String role;
        do {
            userGetRoleMenu();
            role = scanner.nextLine();
            switch (role) {
                case "1" -> {
                    return Role.STUDENT.getRoleName();
                }
                case "2" -> {
                    return Role.TEACHER.getRoleName();
                }
                case "3" -> {
                    return Role.ADMIN.getRoleName();
                }
                default -> System.out.println("Bloda ivestis!");
            }
        } while (true);
    }

    private void userGetRoleMenu() {
        System.out.println("""
                1 - STUDENT
                2 - TEACHER
                3 - ADMIN
                """);
    }

    public User getUser(List<User> users) {
        User user;
        do {
            Long userId = getCorrectNumber();
            user = users.stream()
                    .filter(u -> u.getUserId().equals(userId))
                    .findFirst()
                    .orElse(null);
            if (user == null) {
                System.out.print("Pasirinkite vartotoja: ");
            }
        } while (user == null);

        return user;
    }
}
