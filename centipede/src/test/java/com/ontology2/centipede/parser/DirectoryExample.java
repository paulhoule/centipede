package com.ontology2.centipede.parser;

import java.io.File;
import java.util.List;

public class DirectoryExample implements HasOptions {
    @Option(description="default directory")
    public String dir;

    @Option(description="left directory")
    public String leftDir;

    @Option(description="right directory")
    public String rightDir;

    @Option(description="output directory")
    public String outputDir;

    @Option(description="left",contextualConverter=DirectoryExample.LeftSubstitutor.class)
    public List<String> left;

    @Option(description="right")
    public List<String> right;

    @Option(description="output")
    public String output;

    public static class LeftSubstitutor implements ContextualConverter<String> {

        public String convert(String value, HasOptions that) {
            String defaultDir=((DirectoryExample) that).leftDir;
            if(defaultDir.isEmpty()) {
                defaultDir=((DirectoryExample) that).dir;
            }

            if(defaultDir.isEmpty())
                return value;

            File there=new File(defaultDir,value);
            return there.getPath();
        }
    }
}
