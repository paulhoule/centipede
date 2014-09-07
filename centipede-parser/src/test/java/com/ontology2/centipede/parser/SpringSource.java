package com.ontology2.centipede.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "testContext.xml"})
public class SpringSource {
    @Test
    public void ImOkYoureOk() {
        assertTrue(true);
    }
}
