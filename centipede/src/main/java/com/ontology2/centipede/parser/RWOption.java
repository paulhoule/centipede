package com.ontology2.centipede.parser;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class RWOption  {
    private final Field field;
    private String name;
    private String defaultValue;
    private String description;
    private Type type;

    public RWOption(Field f) {
        Option o=(Option) f.getAnnotation(Option.class);
        this.field=f;
        this.name=o.name();
        this.defaultValue=o.defaultValue();
        this.description=o.description();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type=type;
    }

    public Field getField() {
        return field;
    }
}
