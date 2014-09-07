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

    @Option(description="right",contextualConverter=DirectoryExample.RightSubstitutor.class)
    public List<String> right;

    @Option(description="output",contextualConverter=DirectoryExample.OutputSubstitutor.class)
    public String output;

    public abstract static class DirectorySubstitutor<T> implements ContextualConverter<String> {
        public String convert(String value, HasOptions that) {
            String defaultDir=getDefaultDir((T) that);

            if(defaultDir.isEmpty())
                return value;

            File there=new File(defaultDir,value);
            return there.getPath();
        }

        public abstract String getDefaultDir(T that);
    }

    public static class LeftSubstitutor extends DirectorySubstitutor<DirectoryExample> {
        @Override
        public String getDefaultDir(DirectoryExample that) {
            return that.leftDir.isEmpty() ? that.dir : that.leftDir;
        }
    }

    public static class RightSubstitutor extends DirectorySubstitutor<DirectoryExample> {
        @Override
        public String getDefaultDir(DirectoryExample that) {
            return that.rightDir.isEmpty() ? that.dir : that.rightDir;
        }
    }

    public static class OutputSubstitutor extends DirectorySubstitutor<DirectoryExample> {
        @Override
        public String getDefaultDir(DirectoryExample that) {
            return that.outputDir.isEmpty() ? that.dir : that.outputDir;
        }
    }
}
