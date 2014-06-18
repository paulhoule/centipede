package com.ontology2.centipede.shell;

import com.ontology2.centipede.shell.CommandLineApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("shellTest")
public class ShellTestApp extends CommandLineApplication {
    static boolean gotHit=false;
    static String[] lastArguments;
    @Autowired String launchCode;

    public ShellTestApp() { 
        reset();
    }

    protected void _run(String[] arguments) throws Exception {
        System.out.println("Running shell test application");
        gotHit=true;
        lastArguments=arguments;
        lastLaunchCode=launchCode;
    }

    static void reset() {
        gotHit=false;
        lastArguments=null;
        lastLaunchCode=null;
    }

    static boolean getGotHit() {
        return gotHit;
    }

    public static String[] getLastArguments() {
        return lastArguments;
    }

    static String lastLaunchCode;
    public static String getLaunchCode() {
        return lastLaunchCode;
    }
}
