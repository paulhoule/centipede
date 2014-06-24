package com.ontology2.centipede.shell;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.annotations.VisibleForTesting;
import com.ontology2.centipede.parser.OptionParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;
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

    @VisibleForTesting
    static ApplicationContext newContext(List<String> applicationContextPath) {
        return new ClassPathXmlApplicationContext(applicationContextPath.toArray(new String[]{}));
    }

    @VisibleForTesting
    static ApplicationContext newContext(List<String> applicationContextPath,boolean beLazy) {
        if(beLazy) {
            applicationContextPath.add("classpath:com/ontology2/centipede/shell/addLazinessAttributeToAllBeanDefinitions.xml");
        } else {
            applicationContextPath.add("classpath:com/ontology2/centipede/shell/addEagernessAttributeToAllBeanDefinitions.xml");
        }
        AbstractApplicationContext context=new ClassPathXmlApplicationContext(applicationContextPath.toArray(new String[]{}));
        return(ApplicationContext) context;
    }

    private ApplicationContext context;
    @Override
    protected void _run(String[] arguments) throws Exception {
        CentipedeShellOptions centipedeOptions = parseOptions(arguments);

        List<String> contextPath=getApplicationContextPath();

        context = createApplicationContext(centipedeOptions, contextPath);
        executePositionalArguments(centipedeOptions.positional);
        closeContext(context);
    }

    @VisibleForTesting
     ApplicationContext createApplicationContext(CentipedeShellOptions centipedeOptions, List<String> contextPath) {
        contextPath.addAll(centipedeOptions.applicationContext);

        if(centipedeOptions.eager && centipedeOptions.lazy)
            throw new MisconfigurationException("Cannot force eager and lazy load at same time");

        Boolean forcedMode=false;
//        Boolean forcedMode =
//                centipedeOptions.lazy ? true :
//                        (centipedeOptions.eager ? false : null);

        forcedMode = (forcedMode==null) ? isLazyByDefault() : forcedMode;

        return (forcedMode==null) ? newContext(contextPath) :
                newContext(contextPath,forcedMode);
    }

    private CentipedeShellOptions parseOptions(String[] arguments) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ApplicationContext bootstrapContext=newContext(getBootstrapApplicationContextPath());
        OptionParser parser=new OptionParser(CentipedeShellOptions.class);

        wireupOptionParser(bootstrapContext, parser);
        closeContext(bootstrapContext);
        return (CentipedeShellOptions)
                parser.parse(Lists.newArrayList(arguments));
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

    //
    // return true if you want to force lazy loading for everything,  false if you want to
    // force eagerness for everything,  null if you don't override this behavior
    //

    protected Boolean isLazyByDefault() {
        return true;
    }

}
