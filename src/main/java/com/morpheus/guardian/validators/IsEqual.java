package com.morpheus.guardian.validators;

import com.morpheus.guardian.core.Validatable;
import com.morpheus.guardian.core.Validator;

import java.util.Collections;
import java.util.List;

public class IsEqual<T> implements Validator<T> {
    public IsEqual(T value) {
    }

    public static <T> Validator<T> equalTo(T value) {
        return new IsEqual<>(value);
    }

    @Override
    public List<Error> validate(Validatable<T> validatable) {
        return Collections.emptyList();
    }
}
