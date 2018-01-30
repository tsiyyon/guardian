package com.morpheus.guardian;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.morpheus.guardian.dsl.DSL.from;
import static com.morpheus.guardian.validators.IsNull.nil;

public class WhereTest {
    private Map<String, String> context;

    @Before
    public void setUp() {
        context = new HashMap<>();
    }

    @Test
    public void should_able_to_filter_criteria() {
        from(context).where(c -> c.path("$.test").is("test")).select(s -> s.path("xxx")).should(nil());
        from(context).where(c -> c.path("$.test").eq("test")).select(s -> s.path("xxx")).should(nil());
        from(context).where(c -> c.path("$.test").not("test")).select(s -> s.path("xxx")).should(nil());
    }
}
