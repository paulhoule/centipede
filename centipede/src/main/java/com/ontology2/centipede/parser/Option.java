package com.ontology2.centipede.parser;

import com.google.common.base.Function;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
public @interface Option {
    String name() default "";
    String defaultValue() default "";
    String description();
}
