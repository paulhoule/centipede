package com.ontology2.centipede.parser;

import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

import java.beans.PropertyEditorManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import com.google.common.collect.PeekingIterator;
import com.ontology2.centipede.shell.InvalidOptionException;
import com.ontology2.centipede.shell.UnparsableDefaultException;
import com.ontology2.centipede.shell.UnparsableOptionException;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.ConversionFailedException;

public class OptionParser {
    private final Class that;
    @Autowired ConversionService conversionService;

    public OptionParser(Class that) {
        if (that.isAssignableFrom(HasOptions.class)) {
            throw new IllegalArgumentException("Class " + that + " doesn't implement HasOptions");
        }
        this.that = that;
    }

    public HasOptions parse(List<String> args) throws IllegalAccessException {
        HasOptions options;
        try {
            options = (HasOptions) that.getConstructor().newInstance();
        } catch (NoSuchMethodException ex) {
            throw new IllegalArgumentException("Class " + that + " doesn't have a zero argument constructor", ex);
        } catch (IllegalAccessException ex) {
            throw new IllegalArgumentException("Class " + that + " has a non-public zero argument constructor", ex);
        } catch (InstantiationException ex) {
            throw new IllegalArgumentException("Class " + that + " cannot be abstract", ex);
        } catch (InvocationTargetException ex) {
            throw new IllegalArgumentException("Class " + that + " threw an exception during construction", ex);
        }
        Map<String, RWOption> lookup = getStringAnnotationMap(that);
        for(RWOption o:lookup.values()) {
            if(isSomeKindOfBoolean(o)) {
                o.getField().setBoolean(options,false);
            }  else {
                try {
                    o.getField().set(options,
                            conversionService.convert(
                                    o.getDefaultValue()
                                    , TypeDescriptor.valueOf(String.class)
                                    , new TypeDescriptor(o.getField()))
                    );
                } catch(ConversionFailedException x) {
                    throw new UnparsableDefaultException(o.getName(),o.getDefaultValue(),o.getType(),x);
                }
            }
        };

        PeekingIterator<String> p= Iterators.peekingIterator(args.iterator());
        while(p.hasNext() && p.peek().startsWith("-")) {
            String name=p.next().substring(1);
            if(!lookup.containsKey(name))
                throw new InvalidOptionException("invalid option :"+name);

            RWOption field=lookup.get(name);
            if(isSomeKindOfBoolean(field)) {
                field.getField().setBoolean(options,true);
            } else {
                String value=p.next();
                try {
                    field.getField().set(
                            options,
                            conversionService.convert(
                                    value
                                    , TypeDescriptor.valueOf(String.class)
                                    , new TypeDescriptor(field.getField())
                            )
                    );
                } catch(ConversionFailedException x) {
                    throw new UnparsableOptionException(name,value,field.getType(),x);
                }
            }

        }
        return options;
    }

    private boolean isSomeKindOfBoolean(RWOption field) {
        Type t=field.getType();
        return t.equals(Boolean.TYPE) || t.equals(Boolean.class);
    }


    static Map<String, RWOption> getStringAnnotationMap(Class that) {
        Map<String, RWOption> lookup = Maps.newHashMap();
        for (Field f : that.getFields()) {
            RWOption o = new RWOption(f);

            if (o != null) {
                if (o.getName().isEmpty()) {
                    o.setName(f.getName());
                }

                o.setType(f.getGenericType());
                lookup.put(o.getName(), o);
            }
        }
        return lookup;
    }
}
