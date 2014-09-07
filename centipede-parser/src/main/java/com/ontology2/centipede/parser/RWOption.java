package com.ontology2.centipede.parser;

import com.ontology2.centipede.errors.MisconfigurationException;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class RWOption  {
    private final Field field;
    private final Class substitutor;

    private final boolean isRequired;
    private String name;
    private String defaultValue;
    private String description;
    private Type type;

    public RWOption(Field f) {
        Option o=f.getAnnotation(Option.class);
        this.isRequired=(f.getAnnotation(Required.class)!=null);

        this.field=f;
        this.name=o.name();
        this.defaultValue=o.defaultValue();
        this.description=o.description();
        if(!Object.class.equals(o.contextualConverter())
            &&  !ContextualConverter.class.isAssignableFrom(o.contextualConverter()))
            throw new MisconfigurationException("A contextualConverter must be a ContextualConverter for option "+name);
        this.substitutor=o.contextualConverter();
    }

    public boolean isRequired() {
        return isRequired;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type=type;
    }

    public Field getField() {
        return field;
    }

    public boolean isList() {
        return type instanceof ParameterizedType
                && List.class.isAssignableFrom(
                (Class) ((ParameterizedType) type).getRawType());
    }

    public Class getContextualConverter() {
        return substitutor;
    }

    //
    // Only valid if the field is a List<?>,  something horrible is likely to happen otherwise
    //

    public Class getElementType() {
        ParameterizedType that=(ParameterizedType) type;
        return (Class) that.getActualTypeArguments()[0];
    }

    public Object convertFrom(HasOptions context,ConversionService conversionService,String value) throws IllegalAccessException {
        if(Object.class.equals(getContextualConverter())) {
            Type toType= isList() ? getElementType() : getType();
            return conversionService.convert(
                    value
                    , TypeDescriptor.valueOf(String.class)
                    , TypeDescriptor.valueOf((Class) toType)
            );
        } else {
            try {
                ContextualConverter<?> cc=(ContextualConverter <?>) getContextualConverter().newInstance();
                return cc.convert(value, context);
            } catch(InstantiationException x) {
                throw new MisconfigurationException("The contextual converter "+getContextualConverter()+" must have a zero argument constructor");
            }
        }
    };
}
