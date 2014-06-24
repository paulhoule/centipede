package com.ontology2.centipede.shell;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.ontology2.centipede.parser.OptionParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import org.springframework.core.convert.ConversionService;

public class CentipedeShell extends CommandLineApplication {

    private static Log logger = LogFactory.getLog(CentipedeShell.class);
    public CentipedeShell() {

    }

    public List<String> getApplicationContextPath() {
        return Lists.newArrayList("com/ontology2/centipede/shell/applicationContext.xml");
    }

    private List<String> getBootstrapApplicationContextPath() {
        return Lists.newArrayList("com/ontology2/centipede/shell/bootstrapContext.xml");
    }

    private static ApplicationContext newContext(List<String> applicationContextPath) {
        return new ClassPathXmlApplicationContext(applicationContextPath.toArray(new String[]{}));
    }

    private ApplicationContext context;
    @Override
    protected void _run(String[] arguments) throws Exception {
        ApplicationContext bootstrapContext=newContext(getBootstrapApplicationContextPath());
        OptionParser parser=new OptionParser(CentipedeShellOptions.class);

        wireupOptionParser(bootstrapContext, parser);
        CentipedeShellOptions bootstrapOptions=(CentipedeShellOptions)
                parser.parse(Lists.newArrayList(arguments));
        closeContext(bootstrapContext);

        List<String> contextPath=getApplicationContextPath();
        contextPath.addAll(bootstrapOptions.applicationContext);

        context=newContext(contextPath);
        executePositionalArguments(bootstrapOptions.positional);
        closeContext(context);
    }

    private void closeContext(ApplicationContext that) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        that.getClass().getMethod("close").invoke(that);
    }

    private void executePositionalArguments(List<String> argumentList) {
        String[] arguments=argumentList.toArray(new String[0]);
        if(arguments.length==0) {
            usage();
        }

        String action=arguments[0];
        if(action.equals("run")) {
            runAction(arguments);
        } else if(action.equals("list")) {
            listAction(arguments);
        } else {
            usage();
        }
    }

    private void wireupOptionParser(ApplicationContext bootstrapContext, OptionParser parser) {
        parser.conversionService=(ConversionService) bootstrapContext.getBean("conversionService");
    }

    private void listAction(String[] arguments) {
        Map<String, CommandLineApplication> all = context.getBeansOfType(CommandLineApplication.class);
        for(Entry<String, CommandLineApplication> that:all.entrySet()) {
            String beanName=that.getKey();
            System.out.println(beanName);
        }
    }

    public void runAction(String[] arguments) {
        if (arguments.length<2) {
            usage();
        }

        String application=arguments[1];
        CommandLineApplication app=null;
        try {
            app = context.getBean(application,CommandLineApplication.class);
        } catch(BeanNotOfRequiredTypeException ex) {
            die("Application ["+application+"] not found");
        } catch(NoSuchBeanDefinitionException ex) {
            die("Application ["+application+"] not found");
        } ;

        String[] innerArguments= arguments.length<3 
                ? new String[0] 
                : Arrays.copyOfRange(arguments, 2, arguments.length);
        
        app.run(innerArguments);
    }


    /**
     * @return the name of the shell script that wraps this application
     */
    public String getShellName() {
        return "centipede";
    }

    private void usage() {
        System.out.println("usage:");
        System.out.println();
        System.out.println(getShellName()+" list");
        System.out.println(getShellName()+" run <application> ...");
        System.out.println();
        System.out.println("<action> = run");
        System.out.println("<application> the name of the application that you want to run");
        System.out.println();
        System.out.println("Additional parameters are passed to the application");
        System.exit(-1);
    }

}
