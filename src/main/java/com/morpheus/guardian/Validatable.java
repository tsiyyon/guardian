package com.morpheus.guardian;

import com.morpheus.guardian.core.Validator;

import java.util.List;
import java.util.Map;

public class Validatable<T> implements com.morpheus.guardian.core.Validatable<T> {
    private final Object context;
    private String filter;
    private String name;

     private Validatable(Object context, String filter) {
        this.context = context;
        this.filter = filter;
        this.name = filter.replaceAll(".", "");
    }

    @Override
    public Validatable<T> field(String expression) {
        return new Validatable<>(context, expression);
    }

    @Override
    public T value() {
        if (Map.class.isAssignableFrom(context.getClass())) {
            return (T) ((Map) context).get("filter");
        }
        return null;
    }

    String pointer() {
        return filter;
    }

    String name() {
        return name;
    }

    public static <T> Validatable<T> from(Object context, String expression) {
        return new Validatable<>(context, expression);
    }

    @Override
    public List<Validator.Error> should(Validator<T> validator) {
        return validator.validate(this);
    }
}
