package com.morpheus.guardian.validators;

import com.morpheus.guardian.core.Validatable;
import com.morpheus.guardian.core.Validator;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IsEqual<T> implements Validator<T> {
    private T expected;

    public IsEqual(T expected) {
        this.expected = expected;
    }

    public static <T> Validator<T> eq(T operand) {
        return new IsEqual<>(operand);
    }

    @Override
    public List<Error> validate(Validatable<T> validatable) {
        T actual = validatable.value();
        if (actual == null && expected == null) {
            return Collections.emptyList();
        }

        if (actual != null && expected != null) {
            if (isArray(actual)) {

            } else {
                return actual.equals(expected) ? Collections.emptyList() : new ArrayList<Error>(){{
                    add(new Error());
                }};
            }
        } else {
            List<Error> errors = new ArrayList<>();
            errors.add(new Error());
            return errors;
        }


        return Collections.emptyList();
    }

    private static boolean areEqual(Object actual, Object expected) {
        if (actual == null) {
            return expected == null;
        }

        if (expected != null && isArray(actual)) {
            return isArray(expected) && areArraysEqual(actual, expected);
        }

        return actual.equals(expected);
    }

    private static boolean areArraysEqual(Object actualArray, Object expectedArray) {
        return areArrayLengthsEqual(actualArray, expectedArray) && areArrayElementsEqual(actualArray, expectedArray);
    }

    private static boolean areArrayLengthsEqual(Object actualArray, Object expectedArray) {
        return Array.getLength(actualArray) == Array.getLength(expectedArray);
    }

    private static boolean areArrayElementsEqual(Object actualArray, Object expectedArray) {
        for (int i = 0; i < Array.getLength(actualArray); i++) {
            if (!areEqual(Array.get(actualArray, i), Array.get(expectedArray, i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isArray(Object o) {
        return o.getClass().isArray();
    }
}
