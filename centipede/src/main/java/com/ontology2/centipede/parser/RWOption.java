package com.ontology2.centipede.parser;

public class RWOption  {
    private String name;
    private String defaultValue;
    private String description;

    public RWOption(Option o) {
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
}
