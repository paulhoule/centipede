package com.ontology2.centipede.parser;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.beans.PropertyEditorManager;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.PeekingIterator;
import com.ontology2.centipede.shell.InvalidOptionException;
import com.ontology2.centipede.shell.MisconfigurationException;
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
    @Autowired
    ConversionService conversionService;

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
        for (RWOption o : lookup.values()) {
            if (isSomeKindOfBoolean(o)) {
                o.getField().setBoolean(options, false);
            } else {
                Object defaultValue = null;
                if (!o.getDefaultValue().isEmpty()) {
                    try {
                        defaultValue = conversionService.convert(
                                o.getDefaultValue()
                                , TypeDescriptor.valueOf(String.class)
                                , new TypeDescriptor(o.getField())
                        );
                    } catch (ConversionFailedException x) {
                        throw new UnparsableDefaultException(o.getName(), o.getDefaultValue(), o.getType(), x);
                    }
                } else {
                    defaultValue = defaultValueFor(o.getType());
                }

                o.getField().set(options, defaultValue);
            }
        }

        PeekingIterator<String> p = Iterators.peekingIterator(args.iterator());
        while (p.hasNext() && p.peek().startsWith("-")) {
            String name = p.next().substring(1);
            if (!lookup.containsKey(name))
                throw new InvalidOptionException("invalid option :" + name);

            RWOption field = lookup.get(name);
            if (isSomeKindOfBoolean(field)) {
                field.getField().setBoolean(options, true);
            } else {
                String value = p.next();
                try {
                    if (field.isList()) {
                        Iterable<String> parts = Splitter.on(",").split(value);
                        Class elementType = field.getElementType();
                        for (String part : parts) {
                            final Object innerValue = field.convertFrom(options, conversionService, part);
                            ((List) field.getField().get(options)).add(innerValue);
                        }
                    } else {
                        final Object innerValue = field.convertFrom(options, conversionService, value);
                        field.getField().set(options,innerValue);
                    }
                } catch (ConversionFailedException x) {
                    throw new UnparsableOptionException(name, value, field.getType(), x);
                }
            }
        }

        List<String> positional=Lists.newArrayList(p);
        Field positionalField=findPositionalParameter(that);
        if(positionalField!=null)
            positionalField.set(options,positional);

        return options;
    }


    private boolean isSomeKindOfBoolean(RWOption field) {
        Type t = field.getType();
        return t.equals(Boolean.TYPE) || t.equals(Boolean.class);
    }

    //
    // These are similar to the JLS default values
    //
    // http://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html
    //
    // with the introduction of PHP-isms such as the empty string defaulting to a String,
    // a collection defaulting to an empty collection,  etc.
    //

    static Object defaultValueFor(Type type) {
        if (Byte.TYPE.equals(type)) {
            return (byte) 0;
        }
        ;

        if (Short.TYPE.equals(type)) {
            return (short) 0;
        }

        if (Integer.TYPE.equals(type)) {
            return (int) 0;
        }

        if (Long.TYPE.equals(type)) {
            return 0L;
        }

        if (Float.TYPE.equals(type)) {
            return 0.0F;
        }

        if (Double.TYPE.equals(type)) {
            return 0.0;
        }

        if (Boolean.TYPE.equals(type)) {
            return false;
        }

        if (Character.TYPE.equals(type)) {
            return '\0';
        }

        if (String.class.equals(type)) {
            return "";
        }

        if (List.class.equals(type)) {
            return new ArrayList();
        }

        if (type instanceof ParameterizedType) {
            ParameterizedType generic = (ParameterizedType) type;
            if (List.class.isAssignableFrom((Class) generic.getRawType())) {
                return new ArrayList();
            }
        }

        return null;
    }


    static Map<String, RWOption> getStringAnnotationMap(Class that) {
        Map<String, RWOption> lookup = Maps.newHashMap();
        for (Field f : that.getFields()) {
            if(null==f.getAnnotation(Option.class))
                continue;

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

    static Field findPositionalParameter(Class that) {
        for (Field f : that.getFields())
            if(f.getAnnotation(Positional.class)!=null) {
                if(!List.class.isAssignableFrom(f.getType()))
                    throw new MisconfigurationException("The @Positional parameter must be a List<String>");

                ParameterizedType generic=(ParameterizedType) f.getGenericType();
                if(!String.class.equals(generic.getActualTypeArguments()[0]))
                    throw new MisconfigurationException("The @Positional parameter must be a List<String>");

                return f;
            }
        return null;
    }
}
