package com.ontology2.centipede.parser;

import com.ontology2.centipede.parser.Option;

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

    @Option(description="left")
    public List<String> left;

    @Option(description="right")
    public List<String> right;

    @Option(description="output")
    public String output;

    public static class LeftSubstitutor implements Substitutor<String,DirectoryExample> {

        public String substitute(String value, DirectoryExample that) {
            String defaultDir=that.leftDir;
            if(defaultDir.isEmpty()) {
                defaultDir=that.dir;
            }

            if(defaultDir.isEmpty())
                return value;

            File there=new File(defaultDir,value);
            return there.getPath();
        }
    }
}
