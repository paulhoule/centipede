package com.ontology2.centipede.shell;

import com.ontology2.centipede.shell.CommandLineApplication;
import org.springframework.stereotype.Component;

@Component("shellTest")
public class ShellTestApp extends CommandLineApplication {
    static boolean gotHit=false;
    static String[] lastArguments;

    public ShellTestApp() { 
        gotHit=false;
        lastArguments=null;
    }

    protected void _run(String[] arguments) throws Exception {
        System.out.println("Running shell test application");
        gotHit=true;
        lastArguments=arguments;
    }

    static void reset() {
        gotHit=false;
        lastArguments=null;
    }

    static boolean getGotHit() {
        return gotHit;
    }

    public static String[] getLastArguments() {
        return lastArguments;
    }

}
