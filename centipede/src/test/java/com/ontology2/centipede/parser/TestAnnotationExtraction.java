package com.ontology2.centipede.parser;

import org.junit.Test;
import static com.ontology2.centipede.parser.OptionParser.defaultValueFor;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
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
        assertEquals(3,lookup.size());
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

    @Test
    public void testDefaults() {

        // 8 primitive types get defaults from the JLS

        assertEquals((byte) 0, defaultValueFor(Byte.TYPE));
        assertEquals((short) 0,defaultValueFor(Short.TYPE));
        assertEquals(0,defaultValueFor(Integer.TYPE));
        assertEquals(0L,defaultValueFor(Long.TYPE));

        assertEquals(0.0f,defaultValueFor(Float.TYPE));
        assertEquals(0.0,defaultValueFor(Double.TYPE));
        assertEquals(false,defaultValueFor(Boolean.TYPE));
        assertEquals('\0',defaultValueFor(Character.TYPE));

        // For a string we get the empty String

        assertEquals("",defaultValueFor(String.class));
        assertEquals(new ArrayList(),defaultValueFor(List.class));

        ParameterizedType t=(ParameterizedType)
                ((new ArrayList<Exception>())
                .getClass()
                .getGenericSuperclass());

        assertEquals(new ArrayList<Exception>(),defaultValueFor(t));
        assertEquals(new ArrayList<Long>(),defaultValueFor(t));
    }
}
