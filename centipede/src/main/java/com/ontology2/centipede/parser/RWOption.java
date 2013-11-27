package com.ontology2.centipede.parser;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class RWOption  {
    private final Field field;
    private final Class substitutor;
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
        this.substitutor=o.substitutor();
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

    public boolean isList() {
        return type instanceof ParameterizedType
                && List.class.isAssignableFrom(
                (Class) ((ParameterizedType) type).getRawType());
    }
    public Class getSubstitutor() {
        return substitutor;
    }

    //
    // Only valid if the field is a List<?>,  something horrible is likely to happen otherwise
    //

    public Class getElementType() {
        ParameterizedType that=(ParameterizedType) type;
        return (Class) that.getActualTypeArguments()[0];
    }
}
