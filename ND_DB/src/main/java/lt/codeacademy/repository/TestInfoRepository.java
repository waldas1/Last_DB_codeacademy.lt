package lt.codeacademy.repository;

import lt.codeacademy.entity.TestInfo;


public class TestInfoRepository extends AbstractRepository{

    public void createTestInfo(TestInfo testInfo){
        modifyEntity(session -> session.persist(testInfo));
    }
}
