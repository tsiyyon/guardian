package com.morpheus.guardian.core;

import java.util.List;

public interface Validatable<T> {
    Validatable<T> field(String expression);

    List<Validator.Error> should(Validator<T> validator);

    T value();
}
