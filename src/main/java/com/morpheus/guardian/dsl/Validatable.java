package com.morpheus.guardian.dsl;

import com.morpheus.guardian.core.Validator;

import java.util.List;

public class Validatable<T> implements com.morpheus.guardian.core.Validatable<T> {
    private Object context;
    private Addressable<T> addressable;
    private String filter;
    private String name;

    public Validatable(Object context, Addressable<T> addressable) {
        this.context = context;
        this.addressable = addressable;
    }

    @Override
    public T value() {
        if (context == null) {
            return null;
        }

        if (addressable == null) {
            return (T) context;
        }

        return addressable.locate(context);
    }

    @Override
    public <R> com.morpheus.guardian.core.Validatable<R> nested(Addressable<R> addressable) {
        return new Validatable<>(context, addressable);
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
}
