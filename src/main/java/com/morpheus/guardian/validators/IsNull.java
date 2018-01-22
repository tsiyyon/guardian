package com.morpheus.guardian.validators;

import com.morpheus.guardian.core.Validatable;
import com.morpheus.guardian.core.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.morpheus.guardian.validators.Not.not;


public class IsNull<T> implements Validator<T> {

    public static <T> Validator<T> nil() {
        return new IsNull<>();
    }

    public static <T> Validator<T> notNil() {
        return not(nil());
    }

    @Override
    public List<Error> validate(Validatable<T> validatable) {
         T actual = validatable.value();

        if (actual == null) {
            return Collections.emptyList();
        } else {
            ArrayList<Error> errors = new ArrayList<>();
            errors.add(new Error());
            return errors;
        }
    }
}
