package com.ontology2.centipede.parser;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestDirectoryExample extends SpringSource {

    @Resource
    OptionParser directoryExampleParser;

    @Test
         public void substituteIntoLeft() throws IllegalAccessException {
        List<String> args= Lists.newArrayList(
                "-dir"
                ,"/var/left/"
                ,"-left"
                ,"chomsky,albert,zinn"
        );

        DirectoryExample ex=(DirectoryExample) directoryExampleParser.parse(args);
        List<String> expectedleft=Lists.newArrayList(
                new File("/var/left/chomsky").toString(),
                new File("/var/left/albert").toString(),
                new File("/var/left/zinn").toString()
        );

        assertEquals(expectedleft, ex.left);
    }

    @Test
    public void substituteIntoAll() throws IllegalAccessException {
        List<String> args= Lists.newArrayList(
                "-leftDir"
                ,"/var/left/"
                ,"-rightDir"
                ,"/var/right/"
                ,"-dir"
                ,"/var/default"
                ,"-outputDir"
                ,"/var/output/"
                ,"-left"
                ,"chomsky,albert,zinn"
                ,"-right"
                ,"buchanan,buckley,vonMises"
                ,"-output"
                ,"differences"
        );

        DirectoryExample ex=(DirectoryExample) directoryExampleParser.parse(args);

        List<String> expectedleft=Lists.newArrayList(
                new File("/var/left/chomsky").toString(),
                new File("/var/left/albert").toString(),
                new File("/var/left/zinn").toString()
        );


        List<String> expectedright=Lists.newArrayList(
                new File("/var/right/buchanan").toString(),
                new File("/var/right/buckley").toString(),
                new File("/var/right/vonMises").toString()
        );

        assertEquals(expectedright, ex.right);
        assertEquals(expectedleft, ex.left);
        assertEquals(new File("/var/output/differences").toString(),ex.output);
    }
}
