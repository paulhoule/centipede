package com.ontology2.centipede.parser;

//
// a ContextualConverter had better have a zero-arg constructor
//

public interface ContextualConverter<FieldType> {
    public FieldType convert(String value, HasOptions that);
}
