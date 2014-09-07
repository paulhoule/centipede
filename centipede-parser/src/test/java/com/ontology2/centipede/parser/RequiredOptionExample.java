package com.ontology2.centipede.parser;

import com.ontology2.centipede.parser.HasOptions;
import com.ontology2.centipede.parser.Option;
import com.ontology2.centipede.parser.Required;

public class RequiredOptionExample implements HasOptions {
    @Option(description="this option is required") @Required
    public String aRequiredOption;
}
