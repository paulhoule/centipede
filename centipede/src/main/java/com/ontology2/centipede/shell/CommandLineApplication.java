package com.ontology2.centipede.shell;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class CommandLineApplication {
    private static Log logger = LogFactory.getLog(CommandLineApplication.class);

    public void run(String[] arguments) {
        try {
            _run(arguments);
        } catch (ExitCodeException e) { 
            logger.error("process failed with exit code"+e.getStatus(),e);
            System.exit(e.getStatus());
        } catch(ShutTheProcessDown e) {
            System.exit(1);    // should we have a way to change this?
        } catch(Exception e) {
            logger.error("Uncaught exception in application",e);
        }
    }

    protected abstract void _run(String[] arguments) throws Exception;

    protected void die(String message) {
        System.err.println(message);
        throw new ShutTheProcessDown();
    }

}
