package com.ontology2.centipede.shell;

public class InvalidOptionException extends UsageException {
    public InvalidOptionException() {
    }

    public InvalidOptionException(String message) {
        super(message);
    }

    public InvalidOptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOptionException(Throwable cause) {
        super(cause);
    }

    public InvalidOptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
