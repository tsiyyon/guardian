package com.morpheus.guardian.validators;

import com.morpheus.guardian.core.Validatable;
import com.morpheus.guardian.core.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.morpheus.guardian.validators.IsEqual.eq;

public class Is<T> implements Validator<T> {

    private Validator<T> validator;

    private Is(Validator<T> validator) {
        this.validator = validator;
    }

    public static <T> Validator<T> is(Validator<T> validator) {
        return new Is<>(validator);
    }

    public static <T> Validator<T> is(T value) {
        return is(eq(value));
    }

    @Override
    public List<Error> validate(Validatable<T> validatable) {
        return validator.validate(validatable);
    }
}
