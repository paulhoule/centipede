package com.ontology2.centipede.parser;

import com.google.common.collect.Maps;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class OptionParser {
    private final Class that;

    public OptionParser(Class that) {
        if (HasOptions.class.isAssignableFrom(that)) {
            throw new IllegalArgumentException("Class " + that + " doesn't implement HasOptions");
        }
        this.that = that;
    }

    public HasOptions parse(List<String> args) {
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

        lookup.get("buzz");
        return options;
    }

    static Map<String, RWOption> getStringAnnotationMap(Class that) {
        Map<String, RWOption> lookup = Maps.newHashMap();
        for (Field f : that.getFields()) {
            RWOption o = new RWOption((Option) f.getAnnotation(Option.class));

            if (o != null) {
                if (o.getName().isEmpty()) {
                    o.setName(f.getName());
                }
                lookup.put(o.getName(), o);
            }
        }
        return lookup;
    }
}
