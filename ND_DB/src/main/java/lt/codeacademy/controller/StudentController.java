package lt.codeacademy.controller;

import lt.codeacademy.entity.Questions;
import lt.codeacademy.entity.Test;
import lt.codeacademy.entity.TestInfo;
import lt.codeacademy.entity.User;
import lt.codeacademy.service.TestInfoService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentController extends AbstractController {
    private final Scanner scanner;
    private List<TestInfo> testInfoList = new ArrayList<>();
    private final TestInfoService testInfoService;
    private int countCorrectAnswers = 0;
    private int countWrongAnswers = 0;

    public StudentController() {
        this.scanner = new Scanner(System.in);
        this.testInfoService = new TestInfoService();
    }

    void studentMenuAction(User user) {
        String action;
        do {
            studentMenu();
            action = scanner.nextLine();
            switch (action) {
                case "1" -> startExam(user);
                case "2" -> getGrade(user);
                case "3" -> System.out.println("Exiting!");
                default -> System.out.println("Bloga ivestis!");
            }
        } while (!action.equals("3"));
    }

    private void startExam(User user) {
        Test test = getTestByTestId();
        if (checkIfStudentCanRetakeExam(test)) {
            System.out.println("Galite perlaikyti egzamina");
            showQuestions(test, user);

        } else {
            System.out.println("Perlaikyti sio egzamino negalite! Nuo paskutinio perlaikymo nepraejo 48h");
        }
    }

    public void getGrade(User user) {
        Test test = getTestByTestId();
        testInfoList = user.getTestInfoList();
        for (TestInfo testInfo : testInfoList) {
            System.out.printf("%s - pazymis,\n %s - teisingi tasakymai,\n %s - blogi atsakyma,\n %s - laikimo data,\n %s - testo pavadinimas\n"
                    , testInfo.getGrade(), testInfo.getCorrectAnswers(), testInfo.getWrongAnswers(), testInfo.getStartDate(), test.getTitle());
        }
    }

    private void showQuestions(Test test, User user) {
        List<Questions> questions = test.getQuestions();
        for (Questions question : questions) {
            System.out.printf("%s \n %s \n %s \n %s\n", question.getQuestion(), question.getAnswerOne(), question.getAnswerTwo(), question.getAnswerThree());

            System.out.println("Atsakymas: ");
            String studentAnswer = scanner.nextLine();

            if (studentAnswer.equals(question.getCorrectAnswer())) {
                countCorrectAnswers++;
            } else {
                countWrongAnswers++;
            }

            TestInfo testInfo = new TestInfo(countCorrectAnswers, countCorrectAnswers, countWrongAnswers, LocalDateTime.now());
            testInfo.setTest(test);
            testInfo.setUser(user);
            testInfoService.createTestInfo(testInfo);
        }
    }

    private boolean checkIfStudentCanRetakeExam(Test test) {
        testInfoList = test.getTestInfoList();

        for (TestInfo testInfo : testInfoList) {
            if (!LocalDateTime.now().isAfter(testInfo.getStartDate().plusHours(48))) {
                return false;
            }
        }
        return true;
    }

    private void studentMenu() {
        System.out.println("Prisijungete kaip studentas!");
        System.out.println("""
                1 - Laikyti egzamina
                2 - Perziureti egzaminu laikymo istorija
                3 - Iseiti
                """);
    }
}
