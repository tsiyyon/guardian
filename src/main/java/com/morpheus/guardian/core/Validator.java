package com.morpheus.guardian.core;

import java.util.List;

public interface Validator<T> {
    List<Error> validate(Validatable<T> validatable);

    class Error {
        String code;
        String pointer;
        String status;
        String title;
        String detail;
    }
}
