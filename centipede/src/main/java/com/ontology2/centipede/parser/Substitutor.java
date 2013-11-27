package com.ontology2.centipede.parser;

//
// a Substitutor had better have a zero-arg constructor
//

public interface Substitutor<FieldType,OptionClass extends HasOptions> {
    public FieldType substitute(String value,OptionClass that);
}
