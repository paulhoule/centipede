package com.ontology2.centipede.errors;

import java.lang.reflect.Type;

public class UnparsableOptionException extends RuntimeException {
    private final String optionName;
    private final String invalidValue;
    private final Type targetType;

    public UnparsableOptionException(
            String optionName
            ,String invalidValue
            ,Type targetType
            ,Throwable innerException) {
        super("Could not parse ["+invalidValue+"] for option ["+optionName+"] with target type ["+targetType+"]",innerException);
        this.optionName=optionName;
        this.invalidValue=invalidValue;
        this.targetType=targetType;
    }

    public UnparsableOptionException(
            String optionName
            ,String invalidValue
            ,Type targetType
    ) {
        this(optionName,invalidValue,targetType,null);
    }

    public String getOptionName() {
        return optionName;
    }

    public String getInvalidValue() {
        return invalidValue;
    }

    public Type getTargetType() {
        return targetType;
    }
}
