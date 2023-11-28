package com.daanta.simplecrawler;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
class SimpleCrawlerApplicationTests {

    @Test
    void contextLoads() {
        log.debug("TEST READY.");
        assertTrue(true);
    }

}
