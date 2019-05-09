package com.shiep.fxserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FxServerApplicationTests {
    private Logger logger = LoggerFactory.getLogger(FxServerApplicationTests.class);

    @Test
    public void contextLoads() {
        logger.info("测试log");
        logger.warn("warn log test");
    }

}
