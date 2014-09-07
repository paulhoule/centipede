package com.ontology2.centipede.parser;

import com.ontology2.centipede.parser.HasOptions;
import com.ontology2.centipede.parser.Option;

public class InvalidDefaultExample implements HasOptions {
    @Option(defaultValue="i am the king of hellfire",description="crash -n- burn")
    public int burn;
}
