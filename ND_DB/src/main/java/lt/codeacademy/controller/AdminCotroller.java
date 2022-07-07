package lt.codeacademy.controller;

import lt.codeacademy.entity.User;
import lt.codeacademy.service.UserService;

import java.util.List;
import java.util.Scanner;

public class AdminCotroller extends AbstractController {
    private final Scanner scanner;
    private final UserService userService;

    public AdminCotroller() {
        this.scanner = new Scanner(System.in);
        this.userService = new UserService();
    }

    public void adminMenuAction() {
        String action;
        do {
            adminMenu();
            action = scanner.nextLine();
            switch (action) {
                case "1" -> deleteUser();
                case "2" -> registration();
                case "3" -> getAllUsers();
                case "4" -> System.out.println("Exiting!");
                default -> System.out.println("Bloga ivestis!");
            }
        } while (!action.equals("4"));
    }

    private void deleteUser() {
        LogInController logInController = new LogInController();
        List<User> users = getAllUsers();
        System.out.print("Pasirinkite koki vartotoja norite istrinti: ");
        User user = logInController.getUser(users);

        userService.deleteUserById(user);
    }
    private List<User> getAllUsers() {
        List<User> users = userService.getUsers();
        for (User user : users) {
            System.out.printf("%s %s %s %s\n", user.getUserId(), user.getName(), user.getSurname(), user.getRole());
        }
        return users;
    }

    private void adminMenu() {
        System.out.println("prisijungete kaip admin'as");
        System.out.println("""
                1 - Istrinti esama vartotoja
                2 - Prideti vartotoja
                3 - Visi vartotuojai
                4 - Iseiti
                """);
    }
}
