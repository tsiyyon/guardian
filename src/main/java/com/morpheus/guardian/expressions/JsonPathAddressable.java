package com.morpheus.guardian.expressions;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.morpheus.guardian.core.Validatable;

import static com.jayway.jsonpath.Configuration.builder;
import static com.jayway.jsonpath.Configuration.defaultConfiguration;
import static com.jayway.jsonpath.JsonPath.using;

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
        Configuration config = defaultConfiguration()
                .addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL)
                .addOptions(Option.SUPPRESS_EXCEPTIONS);

        return using(config).parse(context).read(expression);
    }
}
