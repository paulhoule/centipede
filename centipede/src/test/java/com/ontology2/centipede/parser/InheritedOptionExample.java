package com.ontology2.centipede.parser;

public class InheritedOptionExample extends OptionExample implements HasOptions {
    @Option(name="johnny",defaultValue="1234",description="one hundred feet")
    public Long john;

    @Option(description="@jimmy is wearing a hat")
    public long jimmy;
}
