package com.morpheus.guardian;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.morpheus.guardian.dsl.DSL.from;
import static com.morpheus.guardian.validators.IsEqual.eq;
import static com.morpheus.guardian.validators.IsNull.nil;

public class SelectTest {
    private Map<String, String> context;

    @Before
    public void setUp() {
        context = new HashMap<>();
    }

    @Test
    public void should_select_nil_when_context_is_empty() {
        from(context).select(c -> c.path("$.test")).should(nil());
    }

    @Test
    public void should_select_value_when_context_has_value() {
        context.put("test", "test");

        from(context).select(c -> c.path("$.test")).should(eq("test"));
    }
}
