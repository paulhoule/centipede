package com.ontology2.centipede.errors;

public class MisconfigurationException extends RuntimeException {
    public MisconfigurationException() {
    }

    public MisconfigurationException(String message) {
        super(message);
    }

    public MisconfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public MisconfigurationException(Throwable cause) {
        super(cause);
    }

    public MisconfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
