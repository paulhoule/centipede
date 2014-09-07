package com.ontology2.centipede.errors;

public class MissingOptionException extends UsageException {
    public MissingOptionException() {
    }

    public MissingOptionException(String message) {
        super(message);
    }

    public MissingOptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingOptionException(Throwable cause) {
        super(cause);
    }

    public MissingOptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
