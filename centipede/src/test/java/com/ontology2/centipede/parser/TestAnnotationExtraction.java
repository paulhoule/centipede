package com.ontology2.centipede.parser;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class TestAnnotationExtraction {
    @Test
    public void minimalCase() {
        Map<String,RWOption> lookup=OptionParser.getStringAnnotationMap(OptionExample.class);
        assertEquals(1,lookup.size());
        assertTrue(lookup.containsKey("badass"));
        RWOption o=lookup.get("badass");
        assertEquals("badass",o.getName());
        assertEquals("Never a dude like this one",o.getDescription());
        assertEquals("",o.getDefaultValue());
    }

    @Test
    public void inheritanceCase() {
        Map<String,RWOption> lookup=OptionParser.getStringAnnotationMap(InheritedOptionExample.class);
        assertEquals(2,lookup.size());
        assertTrue(lookup.containsKey("badass"));
        RWOption o1=lookup.get("badass");
        assertEquals("badass",o1.getName());
        assertEquals("Never a dude like this one",o1.getDescription());
        assertEquals("",o1.getDefaultValue());
        RWOption o2=lookup.get("johnny");
        assertEquals("johnny",o2.getName());
        assertEquals("one hundred feet",o2.getDescription());
        assertEquals("1234",o2.getDefaultValue());
    }
}
