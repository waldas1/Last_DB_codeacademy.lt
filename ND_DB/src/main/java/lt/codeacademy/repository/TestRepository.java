package lt.codeacademy.repository;

import lt.codeacademy.entity.Questions;
import lt.codeacademy.entity.Test;

import java.util.List;

public class TestRepository extends AbstractRepository {

    public void createTest(Test test) {
        modifyEntity(session -> session.persist(test));
    }

    public void createQuestions(Questions questions) {
        modifyEntity(session -> session.persist(questions));
    }

    public List<Test> getTests() {
        return getValue(session -> session.createQuery("from Test", Test.class).list());
    }

    public void deleteQuestion(Questions question) {
        modifyEntity(session -> session.delete(question));
    }

    public void updateQuestion(Questions question) {
        modifyEntity(session -> session.update(question));
    }

    public void deleteTest(Test test) {
        modifyEntity(session -> session.delete(test));
    }
}
