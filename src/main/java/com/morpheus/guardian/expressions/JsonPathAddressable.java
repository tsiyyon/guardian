package com.morpheus.guardian.expressions;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.morpheus.guardian.core.Validatable;

public class JsonPathAddressable<T> implements Validatable.Addressable<T> {
    private String expression;

    private JsonPathAddressable(String expression) {
        this.expression = expression;
    }

    public static <T> Validatable.Addressable<T> jpath(String path) {
        return new JsonPathAddressable<>(path);
    }

    @Override
    public T locate(Object context) {
        DocumentContext parse = JsonPath.parse(context);
        return parse.read(expression);
    }
}
