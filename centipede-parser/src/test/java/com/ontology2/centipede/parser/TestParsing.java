package com.ontology2.centipede.parser;

import com.google.common.collect.Lists;
import com.ontology2.centipede.errors.UnparsableDefaultException;
import com.ontology2.centipede.errors.UnparsableOptionException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestParsing extends SpringSource {
    @Resource
    OptionParser exampleOne;

    @Resource
    OptionParser invalidDefault;

    @Test
    public void testDefaultHandling() throws IllegalAccessException {
        InheritedOptionExample ioe = (InheritedOptionExample) exampleOne.parse(new ArrayList<String>());
        assertEquals(1234L, (long) ioe.john);
        assertEquals(false, ioe.badass);
    }

    @Test
    public void testCaseOne() throws IllegalAccessException, InstantiationException {
        InheritedOptionExample ioe = (InheritedOptionExample) exampleOne.parse(
                new ArrayList<String>() {{
                    add("-johnny");
                    add("1234567890");
                }}
        );
        assertEquals(1234567890L, (long) ioe.john);
        assertEquals(false, ioe.badass);
    }

    @Test
    public void testCaseTwo() throws IllegalAccessException, InstantiationException {
        InheritedOptionExample ioe = (InheritedOptionExample) exampleOne.parse(
                new ArrayList<String>() {{
                    add("-badass");
                    add("-johnny");
                    add("77");
                }}
        );
        assertEquals(77L, (long) ioe.john);
        assertEquals(true, ioe.badass);
        assertEquals(0,ioe.jimmy);
        assertTrue(ioe.numbers.isEmpty());
    }

    @Test
    public void assignToJimmy() throws IllegalAccessException, InstantiationException {
        InheritedOptionExample ioe = (InheritedOptionExample) exampleOne.parse(
                new ArrayList<String>() {{
                    add("-jimmy");
                    add("419");
                }}
        );

        assertEquals(ioe.jimmy,419);
    }


    @Test
    public void unparsableOptionFailsAppropriately() throws IllegalAccessException {
        UnparsableOptionException blowup = null;
        try {
            InheritedOptionExample ioe = (InheritedOptionExample) exampleOne.parse(
                    new ArrayList<String>() {{
                        add("-johnny");
                        add("-badass");
                        add("421");
                    }}
            );
        } catch (UnparsableOptionException x) {
            blowup = x;
        }

        assertNotNull(blowup);
        assertEquals("johnny", blowup.getOptionName());
        assertEquals("-badass", blowup.getInvalidValue());
        assertEquals(Long.class, blowup.getTargetType());
    }

    @Test
    public void unparsableDefaultFailsAppropriately() throws IllegalAccessException {
        UnparsableDefaultException blowup = null;
        try {
            invalidDefault.parse(
                    new ArrayList<String>()
            );
        } catch (UnparsableDefaultException x) {
            blowup = x;
        }

        assertNotNull(blowup);
        assertEquals("burn", blowup.getOptionName());
        assertEquals("i am the king of hellfire", blowup.getInvalidValue());
        assertEquals(Integer.TYPE, blowup.getTargetType());
    }

    @Test
    public void parseNumbers() throws IllegalAccessException {
        InheritedOptionExample ioe = (InheritedOptionExample) exampleOne.parse(
                new ArrayList<String>() {{
                    add("-numbers");
                    add("2");
                    add("-numbers");
                    add("4");
                    add("-numbers");
                    add("6");
                    add("-numbers");
                    add("8");
                }}
        );

        List<Integer> rightAnswer= newArrayList(2, 4, 6, 8);
        assertEquals(rightAnswer,ioe.numbers);
    }

    @Test
    public void parseCommaSeparatedNumbers() throws IllegalAccessException {
        InheritedOptionExample ioe = (InheritedOptionExample) exampleOne.parse(
                new ArrayList<String>() {{
                    add("-numbers");
                    add("3,5,7,9");
                }}
        );

        List<Integer> rightAnswer= newArrayList(3, 5, 7, 9);
        assertEquals(rightAnswer,ioe.numbers);
    }

    @Test
    public void positionalWithoutOptions() throws IllegalAccessException {
        final ArrayList<String> args = new ArrayList<String>() {{
            add("ichi");
            add("ni");
            add("san");
            add("shi");
            add("go");
        }};
        InheritedOptionExample ioe = (InheritedOptionExample) exampleOne.parse(args);

        assertEquals(args,ioe.positional);
    }

    @Test
    public void positionalPlusOptions() throws IllegalAccessException {
        final ArrayList<String> positional = new ArrayList<String>() {{
            add("north");
            add("east");
            add("south");
            add("west");
        }};

        List<String> args= newArrayList("-jimmy", "24", "-badass");
        args.addAll(positional);
        InheritedOptionExample ioe = (InheritedOptionExample) exampleOne.parse(args);

        assertEquals(positional,ioe.positional);
        assertEquals(true,ioe.badass);
        assertEquals(24L,ioe.jimmy);
    }
}
