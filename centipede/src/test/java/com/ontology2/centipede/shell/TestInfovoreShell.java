package com.ontology2.centipede.shell;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;

public class TestInfovoreShell {

    @Before
    public void setup() {
        ShellTestApp.reset();
        ObjectThatCountsClassInstances.reset();
    }

    @Test
    public void test() throws IOException {
        String[] arguments = {"run","shellTest"};
        assertFalse(ShellTestApp.getGotHit());
        InfovoreShell.main(arguments);
        assertTrue(ShellTestApp.getGotHit());	
        assertEquals(0,ShellTestApp.getLastArguments().length);
    }

    @Test
    public void testArgs() throws IOException {
        String[] arguments = {"run","shellTest","panic","in","detroit"};
        assertFalse(ShellTestApp.getGotHit());
        InfovoreShell.main(arguments);
        assertTrue(ShellTestApp.getGotHit());	
        assertEquals(3,ShellTestApp.getLastArguments().length);
        assertEquals("panic",ShellTestApp.getLastArguments()[0]);
        assertEquals("in",ShellTestApp.getLastArguments()[1]);
        assertEquals("detroit",ShellTestApp.getLastArguments()[2]);
    }

    @Test
    public void testSingleArg() throws IOException {
        String[] arguments = {"run","shellTest","one"};
        assertFalse(ShellTestApp.getGotHit());
        InfovoreShell.main(arguments);
        assertTrue(ShellTestApp.getGotHit());	
        assertEquals(1,ShellTestApp.getLastArguments().length);
        assertEquals("one",ShellTestApp.getLastArguments()[0]);
    }

    @Test
    public void defaultLaunchCode() throws IOException {
        String[] arguments = {"run","shellTest"};
        InfovoreShell.main(arguments);
        assertTrue(ShellTestApp.getGotHit());
        assertEquals("000-000-000",ShellTestApp.getLaunchCode());
    }

    @Test
    public void iCanEvenLeaveOutTheRun() throws IOException {
        String[] arguments = {"shellTest"};
        InfovoreShell.main(arguments);
        assertTrue(ShellTestApp.getGotHit());
        assertEquals("000-000-000",ShellTestApp.getLaunchCode());
    }

    @Test
    public void alternateLaunchCode() throws IOException {
        String[] arguments = {"-applicationContext","classpath:com/ontology2/centipede/shell/infovoreShellOverrideContext.xml","run","shellTest"};
        InfovoreShell.main(arguments);
        assertTrue(ShellTestApp.getGotHit());
        assertEquals("777-656-005",ShellTestApp.getLaunchCode());
    }

    @Test
    public void evaluationIsLazyByDefault() {
        String[] arguments = {"-applicationContext","classpath:com/ontology2/centipede/shell/objectCountingContext.xml","run","shellTest"};
        InfovoreShell.main(arguments);
        assertEquals(0,ObjectThatCountsClassInstances.getCreated());
    }

    @Test
    public void evaluationCanBeForcedEager() {
        String[] arguments = {"-applicationContext","classpath:com/ontology2/centipede/shell/objectCountingContext.xml","-eager","run","shellTest"};
        InfovoreShell.main(arguments);
        assertEquals(2,ObjectThatCountsClassInstances.getCreated());
    }

    @Test
    public void defaultEvaluationBehaviorCanBeExposed() {
        String[] arguments = {"-applicationContext","classpath:com/ontology2/centipede/shell/objectCountingContext.xml","run","shellTest"};
        NeitherLazyNorEagerShell.main(arguments);
        assertEquals(1,ObjectThatCountsClassInstances.getCreated());
    }

    @Test
    public void defaultEvaluationBehaviorCanBeForcedLazy() {
        String[] arguments = {"-applicationContext","classpath:com/ontology2/centipede/shell/objectCountingContext.xml","-lazy","run","shellTest"};
        NeitherLazyNorEagerShell.main(arguments);
        assertEquals(0,ObjectThatCountsClassInstances.getCreated());
    }

    @Test
    public void defaultEvaluationBehaviorCanBeForcedEager() {
        String[] arguments = {"-applicationContext","classpath:com/ontology2/centipede/shell/objectCountingContext.xml","-eager","run","shellTest"};
        NeitherLazyNorEagerShell.main(arguments);
        assertEquals(2,ObjectThatCountsClassInstances.getCreated());
    }
}
