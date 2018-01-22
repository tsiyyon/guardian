package com.morpheus.guardian.core;

import java.util.List;

public interface Validatable<T> {
    List<Validator.Error> should(Validator<T> validator);

    T value();

    <R> Validatable<R> nested(Addressable<R> addressable);

    interface Addressable<R> {
        R locate(Object context);
    }
}
