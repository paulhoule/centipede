package com.ontology2.centipede.shell;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.ArrayList;

import static com.ontology2.centipede.shell.CentipedeShell.*;
import static junit.framework.TestCase.*;

public class StaticCentipedeShellTest {
    @Before
    public void setup() {
        ObjectThatCountsClassInstances.reset();
    }

    @Test
    public void defaultEvaluationIsDefinedInFiles() {
        ApplicationContext c= newContext(objectCountingContext());
        assertEquals(1, ObjectThatCountsClassInstances.get());
        Object that=c.getBean("l");
        assertEquals(2, ObjectThatCountsClassInstances.get());
        ((AbstractApplicationContext) c).close();
        assertEquals(0, ObjectThatCountsClassInstances.get());
    }

    @Test
    public void lazyEvaluationIsLazy() {
        ApplicationContext c= newContext(objectCountingContext(),true);
        assertEquals(0, ObjectThatCountsClassInstances.get());
        Object that=c.getBean("l");
        assertEquals(1, ObjectThatCountsClassInstances.get());
        Object another=c.getBean("e");
        assertEquals(2, ObjectThatCountsClassInstances.get());
        ((AbstractApplicationContext) c).close();
        assertEquals(0, ObjectThatCountsClassInstances.get());
    }

    @Test
    public void eagerEvaluationIsEager() {
        ApplicationContext c= newContext(objectCountingContext(),false);
        assertEquals(2, ObjectThatCountsClassInstances.get());
        ((AbstractApplicationContext) c).close();
        assertEquals(0, ObjectThatCountsClassInstances.get());
    }



    private ArrayList<String> objectCountingContext() {
        return Lists.newArrayList("classpath:com/ontology2/centipede/shell/objectCountingContext.xml");
    }

}
