package com.ontology2.centipede.parser;

import java.util.List;

public class InheritedOptionExample extends OptionExample implements HasOptions {
    @Option(name="johnny",defaultValue="1234",description="one hundred feet")
    public Long john;

    @Option(description="@jimmy is wearing a hat")
    public long jimmy;

    @Option(description="a garden variety list")
    public List<Integer> numbers;
}
