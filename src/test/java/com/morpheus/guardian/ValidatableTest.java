package com.morpheus.guardian;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.morpheus.guardian.dsl.Validatable.from;
import static com.morpheus.guardian.validators.IsNull.isNull;

public class ValidatableTest {
    private Map<String, String> context;

    @Before
    public void setUp() {
        context = new HashMap<>();
    }

    @Test
    public void should_able_to_create_validatable_by_from() {
        from(context).should(isNull());
        from(context).should(validatable -> validatable.field("test").should(isNull()));

        from(context).select(c -> c.path("test")).should(isNull());
        from(context).where(c -> c.path("test").is("test")).should(isNull());
        from(context).where(c -> c.path("test").is("test")).select(s -> s.path("xxx")).should(isNull());
        from(context).where(c -> c.path("test").eq("test")).select(s -> s.path("xxx")).should(isNull());
        from(context).where(c -> c.path("test").not("test")).select(s -> s.path("xxx")).should(isNull());
    }
}