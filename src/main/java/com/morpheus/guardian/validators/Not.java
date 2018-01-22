package com.morpheus.guardian.validators;

import com.morpheus.guardian.core.Validatable;
import com.morpheus.guardian.core.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.morpheus.guardian.validators.IsEqual.eq;

public class Not<T> implements Validator<T> {
    private Validator<T> validator;

    public Not(Validator<T> validator) {
        this.validator = validator;
    }

    @Override
    public List<Error> validate(Validatable<T> validatable) {
        List<Error> validate = validator.validate(validatable);
        if (validate.size() == 0) {
            List<Error> errors = new ArrayList<>();
            errors.add(new Error());
            return errors;
        } else {
            return Collections.emptyList();
        }
    }


    public static <T> Validator<T> not(Validator<T> validator) {
        return new Not<>(validator);
    }

    public static <T> Validator<T> not(T value) {
        return not(eq(value));
    }
}
