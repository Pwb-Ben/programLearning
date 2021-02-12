package com.programlearning.learning.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestLog {

    private static final Logger logger = LoggerFactory.getLogger(TestLog.class);

    @GetMapping("testLog")
    public String testLog() {
        logger.debug("Something is wrong here");
        logger.info("Something is wrong here");
        logger.error("Something is wrong here");
        logger.warn("Something is wrong here");
        return "Something is wrong here";
    }
}
