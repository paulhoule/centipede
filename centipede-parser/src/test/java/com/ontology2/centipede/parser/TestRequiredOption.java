package com.ontology2.centipede.parser;


import com.google.common.collect.Lists;
import com.ontology2.centipede.errors.MissingOptionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestRequiredOption extends SpringSource {
    @Resource
    OptionParser requiredOptionExampleParser;

    @Test(expected=MissingOptionException.class)
    public void throwsExceptionIfAbsent() throws IllegalAccessException {
        requiredOptionExampleParser.parse(new ArrayList<String>());
    }

    @Test
    public void populatesIfPresent() throws IllegalAccessException {
        RequiredOptionExample roe=(RequiredOptionExample)
                requiredOptionExampleParser.parse(Lists.newArrayList(
                        "-aRequiredOption"
                        ,"urge_prolong"
                ));

        assertEquals("urge_prolong",roe.aRequiredOption);
    }
}
