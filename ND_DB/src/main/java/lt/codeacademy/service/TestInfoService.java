package lt.codeacademy.service;

import lt.codeacademy.entity.TestInfo;
import lt.codeacademy.repository.TestInfoRepository;


public class TestInfoService {

    private final TestInfoRepository repository;

    public TestInfoService(){
        this.repository = new TestInfoRepository();
    }

    public void createTestInfo(TestInfo testInfo){
        repository.createTestInfo(testInfo);
    }
}
