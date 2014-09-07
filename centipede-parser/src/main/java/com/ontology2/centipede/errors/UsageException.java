package com.ontology2.centipede.errors;

public class UsageException extends RuntimeException {
    public UsageException() {
    }

    public UsageException(String message) {
        super(message);
    }

    public UsageException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsageException(Throwable cause) {
        super(cause);
    }

    public UsageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
