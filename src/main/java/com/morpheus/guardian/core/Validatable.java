package com.morpheus.guardian.core;

import java.util.List;

public interface Validatable<T> {
    Validatable<T> field(String expression);

    T value();

    List<Validator.Error> should(Validator<T> validator);
}
