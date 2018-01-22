package com.morpheus.guardian.dsl;

import com.morpheus.guardian.core.*;

import java.util.List;
import java.util.Map;

public class Validatable<T> implements com.morpheus.guardian.core.Validatable<T> {
    private Object context;
    private Expression<T> expression;
    private String filter;
    private String name;

    private Validatable(Object context, String filter) {
        this.context = context;
        this.filter = filter;
        this.name = filter.replaceAll(".", "");
    }

    public Validatable(Object context, Expression<T> expression) {
        this.context = context;
        this.expression = expression;
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

    @Override
    public List<Validator.Error> should(Validator<T> validator) {
        return validator.validate(this);
    }

    public interface Expression<R> {
        R applied(Object context);
    }
}
