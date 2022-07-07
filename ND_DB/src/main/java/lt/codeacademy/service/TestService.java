package lt.codeacademy.service;

import lt.codeacademy.entity.Questions;
import lt.codeacademy.entity.Test;
import lt.codeacademy.repository.TestRepository;

import java.util.List;

public class TestService {

    private final TestRepository repository;

    public TestService() {
        this.repository = new TestRepository();
    }

    public void createTest(Test test) {
        repository.createTest(test);
    }

    public void createQuestions(Questions questions) {
        repository.createQuestions(questions);
    }

    public List<Test> getTests() {
        return repository.getTests();
    }

    public void deleteQuestionById(Questions question) {
        repository.deleteQuestion(question);
    }

    public void updateQuestion(Questions question) {
        repository.updateQuestion(question);
    }

    public void deleteTest(Test test) {
        repository.deleteTest(test);
    }
}
