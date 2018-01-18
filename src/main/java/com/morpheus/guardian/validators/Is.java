package com.morpheus.guardian.validators;

import com.morpheus.guardian.core.Validatable;
import com.morpheus.guardian.core.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Is<T> implements Validator<T> {

    private Validator<T> validator;

    public Is(Validator<T> validator) {
        this.validator = validator;
    }

    public static <T> Validator<T> is(Validator<T> validator) {
        return new Is<>(validator);
    }

    public static <T> Validator<T> is(T value) {
        return is(IsEqual.equalTo(value));
    }

    @Override
    public List<Error> validate(Validatable<T> validatable) {
        T actual = validatable.value();

        if (actual == null) {
            return Collections.emptyList();
        } else {
            ArrayList<Error> errors = new ArrayList<>();
            return errors;
        }
    }
}
