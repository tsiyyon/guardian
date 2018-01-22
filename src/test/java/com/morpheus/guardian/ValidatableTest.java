package com.morpheus.guardian;

import com.morpheus.guardian.core.Validator;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.morpheus.guardian.core.ErrorMatcher.error;
import static com.morpheus.guardian.core.ErrorMatcher.errors;
import static com.morpheus.guardian.dsl.DSL.from;
import static com.morpheus.guardian.expressions.JsonPathAddressable.jpath;
import static com.morpheus.guardian.validators.Is.is;
import static com.morpheus.guardian.validators.IsEqual.eq;
import static com.morpheus.guardian.validators.IsNull.nil;
import static com.morpheus.guardian.validators.Not.not;
import static org.junit.Assert.assertThat;

public class ValidatableTest {
    private Map<String, String> context;

    @Before
    public void setUp() {
        context = new HashMap<>();
    }

    @Test
    public void should_able_to_validate_the_original_context() {
        List<Validator.Error> empty = from(context).should(is(not(nil())));
        assertThat(empty.size(), CoreMatchers.is(0));


        List<Validator.Error> oneError = from(context).should(is(nil()));
        assertThat(oneError.size(), CoreMatchers.is(1));
    }

    @Test
    public void should_able_to_validate_the_null_original_context() {
        List<Validator.Error> empty = from(null).should(nil());
        assertThat(empty.size(), CoreMatchers.is(0));

        List<Validator.Error> oneError = from(null).should(not(nil()));

        assertThat(oneError.size(), CoreMatchers.is(1));
    }

    @Test
    public void should_able_to_get_the_field_and_validated() {
        context.put("test", "test");

        assertThat(from(context)
                .should(
                        validatable -> validatable.nested(jpath("$.test")).should(eq("test"))
                ), errors());

        assertThat(from(context)
                .should(
                        validatable -> validatable.nested(jpath("$.inexistent")).should(nil())
                ), errors());

        assertThat(from(context)
                .should(
                        validatable -> validatable.nested(jpath("$.inexistent")).should(eq("value"))
                ), errors(error("value")));
    }

    @Test
    public void should_use_dsl() {
        from(context).select(c -> c.path("test")).should(nil());
        from(context).where(c -> c.path("test").is("test")).should(nil());
        from(context).where(c -> c.path("test").is("test")).select(s -> s.path("xxx")).should(nil());
        from(context).where(c -> c.path("test").eq("test")).select(s -> s.path("xxx")).should(nil());
        from(context).where(c -> c.path("test").not("test")).select(s -> s.path("xxx")).should(nil());
    }
}