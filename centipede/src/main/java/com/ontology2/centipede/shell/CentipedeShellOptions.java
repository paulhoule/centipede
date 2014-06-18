package com.ontology2.centipede.shell;

import com.ontology2.centipede.parser.HasOptions;
import com.ontology2.centipede.parser.Option;
import com.ontology2.centipede.parser.Positional;

import java.util.List;

public class CentipedeShellOptions implements HasOptions {
    @Option(description="additional application context definitions to load;  these are loaded after the built in context definition and in the order that you declare them.")
    public List<String> applicationContext;

    @Positional
    @Option(description="positional arguments")
    public List<String> positional;
}
