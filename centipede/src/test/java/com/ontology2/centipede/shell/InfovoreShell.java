package com.ontology2.centipede.shell;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.ontology2.centipede.shell.CommandLineApplication;
import com.ontology2.centipede.shell.CentipedeShell;
import org.springframework.stereotype.Component;

import static com.google.common.collect.Iterables.*;
import static com.google.common.collect.Lists.*;

//
// this was once the entrance point for the infovore application,  but when hydroxide was 
// demolished it was moved here together with its test case
// 

public class InfovoreShell extends CentipedeShell{

    @Override
    public String getShellName() {
        return "infovore";
    }

    @Override
    public List<String> getApplicationContextPath() {
        List<String> that=super.getApplicationContextPath();
        that.add("com/ontology2/centipede/shell/infovoreShellContext.xml");
        return that;
    }

    public static void main(String[] args) {
        new InfovoreShell().run(args);


    }


}
