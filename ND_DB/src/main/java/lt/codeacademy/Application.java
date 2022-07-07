package lt.codeacademy;

import lt.codeacademy.controller.LogInController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Application {
    private static final Logger LOG = LogManager.getLogger(Application.class);

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        LogInController logInController = new LogInController();
        LOG.info("Programa pasileido");
        logInController.menuAction(scanner);
    }
}
