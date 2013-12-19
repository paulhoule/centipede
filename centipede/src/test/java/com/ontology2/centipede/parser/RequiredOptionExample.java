package com.ontology2.centipede.parser;

public class RequiredOptionExample implements HasOptions {
    @Option(description="this option is required") @Required
    public String aRequiredOption;
}
