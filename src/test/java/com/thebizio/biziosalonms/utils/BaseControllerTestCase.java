package com.thebizio.biziosalonms.utils;

import com.thebizio.biziosalonms.entity.User;
import com.thebizio.biziosalonms.testcontaines.BaseTestContainer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseControllerTestCase extends BaseTestContainer {

    @Autowired
    DataCleaner dataCleaner;

    @Autowired
    public MvcReqHelper mvcReqHelper;

    @Autowired
    public DemoEntitiesGenerator demoEntitiesGenerator;

    @Autowired
    public MockMvc mvc;

    @BeforeEach
    public void beforeEach() {
        dataCleaner.clean();
        System.out.println("~~~~~~~~~~~~~");
        System.out.println("Data Cleaned");
    }
}
