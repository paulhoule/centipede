package com.ontology2.centipede.parser;

import com.ontology2.centipede.parser.HasOptions;
import com.ontology2.centipede.parser.Option;

public class OptionExample implements HasOptions {
    @Option(description="Never a dude like this one")
    public boolean badass;
}
