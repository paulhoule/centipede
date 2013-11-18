package com.ontology2.centipede.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Version {

    public static String get(String packageName) {
        String lookFor=
                "/"+packageName.replace(".","/")+"/version.properties";
        InputStream in=Version.class.getResourceAsStream(lookFor);
        Properties p=new Properties();
        try {
            p.load(in);
        } catch (IOException e) {
           return null;
        }

        return p.getProperty(packageName+".version");
    }
}
