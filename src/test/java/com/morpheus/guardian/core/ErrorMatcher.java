package com.morpheus.guardian.core;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.core.AllOf.allOf;

public class ErrorMatcher<T> extends BaseMatcher<T> {
    private String expectedCode;

    private ErrorMatcher(String expectedCode) {
        this.expectedCode = expectedCode;
    }

    public static <T> Matcher<T> error(String code) {
        return new ErrorMatcher<>(code);
    }

    @SafeVarargs
    public static <T> Matcher<T> errors(Matcher<T> ...errors) {
        if (errors.length == 0) {
            return new BaseMatcher<T>() {
                @Override
                public boolean matches(Object item) {
                    return true;
                }

                @Override
                public void describeTo(Description description) {

                }
            };
        }

        return allOf(errors);
    }

    @Override
    public boolean matches(Object item) {
        if (item instanceof Validator.Error) {
            Validator.Error error = (Validator.Error) item;
            return expectedCode.equals(error.code);
        }
        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("code:").appendText(expectedCode);
    }
}
