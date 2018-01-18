package com.morpheus.guardian.validators;

import com.morpheus.guardian.core.Validatable;
import com.morpheus.guardian.core.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IsNull<T> implements Validator<T> {

    public static <T> Validator<T> isNull() {
        return new IsNull<>();
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
