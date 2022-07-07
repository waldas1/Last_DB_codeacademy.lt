package lt.codeacademy.controller;

import lt.codeacademy.Enum.Role;
import lt.codeacademy.entity.Questions;
import lt.codeacademy.entity.Test;
import lt.codeacademy.entity.User;
import lt.codeacademy.service.TestService;
import lt.codeacademy.service.UserService;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TeacherCotroller extends AbstractController {

    private final Scanner scanner;
    private final TestService testService;
    private final UserService userService;

    public TeacherCotroller() {
        this.scanner = new Scanner(System.in);
        this.testService = new TestService();
        this.userService = new UserService();
    }

    public void teacherMenuAction() {
        String action;
        do {
            teacherMenu();
            action = scanner.nextLine();
            switch (action) {
                case "1" -> createNewExam();
                case "2" -> updateExam();
                case "3" -> deleteMenuAction();
                case "4" -> getStudentExams();
                case "5" -> System.out.println("Exiting!");
                default -> System.out.println("Bloga ivestis!");
            }
        } while (!action.equals("5"));
    }

    private void deleteMenuAction() {
        String action;
        do {
            deleteMenu();
            action = scanner.nextLine();
            switch (action) {
                case "1" -> deleteExam();
                case "2" -> deleteQuestion();
                case "3" -> System.out.println("Exiting!");
                default -> System.out.println("Wrong input!");
            }
        } while (!action.equals("3"));
    }

    private void deleteExam() {
        List<Test> tests = testService.getTests();
        if (tests.size() == 0) {
            System.out.println("Irasu nera");
            deleteMenuAction();
        }
        for (Test test : tests) {
            System.out.printf("%s %s %s\n", test.getId(), test.getTitle(), test.getType());
        }

        System.out.println("Pasirinkite koki egzamina norite istrinti: ");
        Test test = getExam(tests);

        testService.deleteTest(test);
    }

    private void deleteQuestion() {
        List<Questions> questions = getQuestions();

        System.out.println("Pasirinkite koki Klausyma norite istrinti: ");
        Questions question = getQuestion(questions);

        testService.deleteQuestionById(question);
    }

    private void createNewExam() {
        try {
            System.out.print("Egzamino tipas: ");
            String type = scanner.nextLine();

            System.out.print("Egzamino pavadinimas: ");
            String title = scanner.nextLine();

            Test test = new Test(type, title);
            testService.createTest(test);

            createQuestions(test);

        } catch (InputMismatchException e) {
            System.out.println("Neteisinga ivestis!");
        } catch (Exception e) {
            System.out.println("Brain dmg");
        }
    }

    private void createQuestions(Test test) {
        try {
            System.out.print("Kiek bus klausymu? ");
            int number = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < number; i++) {
                System.out.printf("Iveskite %d klausyma: ", i + 1);
                String question = scanner.nextLine();

                System.out.print("Iveskite pirma atsakyma: ");
                String firstAnswer = scanner.nextLine();

                System.out.print("Iveskite antra atsakyma: ");
                String secondAnswer = scanner.nextLine();

                System.out.print("Iveskite trecia atsakyma: ");
                String thirdAnswer = scanner.nextLine();

                System.out.print("Iveskite teisinga atsakyma: ");
                String correctAnswer = scanner.nextLine();

                Questions questions = new Questions(question, firstAnswer
                        , secondAnswer, thirdAnswer, correctAnswer);
                questions.setTest(test);

                testService.createQuestions(questions);
            }
        } catch (InputMismatchException e) {
            System.out.println("Neteisinga ivestis!");
        } catch (Exception e) {
            System.out.println("Brain dmg");
        }
    }

    private void updateExam() {
        List<Questions> questions = getQuestions();

        System.out.println("Koki klausyma norite redaguoti? Iveskite:");
        Questions question = getQuestion(questions);

        System.out.println("Iveskite nauja klausyma: ");
        String changedQuestion = scanner.nextLine();

        System.out.println("Iveskite pirma atsakyma: ");
        String firstAnswer = scanner.nextLine();

        System.out.println("Iveskite antra atsakyma: ");
        String secondAnswer = scanner.nextLine();

        System.out.println("Iveskite trecia atsakyma: ");
        String thirdAnswer = scanner.nextLine();

        System.out.println("Iveskite teisinga atsakyma: ");
        String correctAnswer = scanner.nextLine();

        question.setQuestion(changedQuestion);
        question.setAnswerOne(firstAnswer);
        question.setAnswerTwo(secondAnswer);
        question.setAnswerThree(thirdAnswer);
        question.setCorrectAnswer(correctAnswer);

        testService.updateQuestion(question);
    }

    private List<Questions> getQuestions() {
        Test test = getTestByTestId();
        List<Questions> questions = test.getQuestions();
        for (Questions question : questions) {
            System.out.printf("%s - id\n %s\n %s\n %s\n %s\n %s\n", question.getId(), question.getQuestion(), question.getAnswerOne(), question.getAnswerTwo(), question.getAnswerThree(), question.getCorrectAnswer());
        }
        return questions;
    }

    private Questions getQuestion(List<Questions> questions) {
        Questions question;
        do {
            Long questionId = getCorrectNumber();
            question = questions.stream()
                    .filter(q -> q.getId().equals(questionId))
                    .findFirst()
                    .orElse(null);
            if (question == null) {
                System.out.println("Pasirinkite koki klausyma norite pakeisti: ");
            }
        } while (question == null);

        return question;
    }

    private void getStudentExams() {
        List<User> users = userService.getUsers();
        for (User user : users) {
            if (user.getRole().equals(Role.STUDENT.getRoleName())) {
                System.out.printf("%s %s %s", user.getUserId(), user.getName(), user.getSurname());
            }
        }
        System.out.print("Iveskite studento id: ");
        User user = getUser(users);

        StudentController studentController = new StudentController();
        studentController.getGrade(user);
    }

    private User getUser(List<User> users) {
        User user;
        do {
            Long userId = getCorrectNumber();
            user = users.stream()
                    .filter(u -> u.getUserId().equals(userId))
                    .findFirst()
                    .orElse(null);

            if (user == null) {
                System.out.print("Pasirinkite useri: ");
            }
        } while (user == null);

        return user;
    }

    private void teacherMenu() {
        System.out.println("prisijungete kaip destytojas");
        System.out.println("""
                1 - Sukurti egzamina
                2 - Redaguoti egzamina
                3 - Trinti
                4 - Perziureti laikytus egzaminus
                5 - Iseiti
                """);
    }

    private void deleteMenu() {
        System.out.println("""
                1 - Istrinti egzamina
                2 - Istrinti klausyma
                3 - Iseiti
                """);
    }
}
