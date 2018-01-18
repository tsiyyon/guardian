package com.morpheus.guardian;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.morpheus.guardian.Validatable.from;
import static org.junit.Assert.*;

public class ValidatableTest {
    private Map<String, String> context;

    @Before
    public void setUp() throws Exception {
        context = new HashMap<>();
    }

    @Test
    public void should_able_to_create_validatable_by_from() {
        Validatable<String> test = from(context, context -> null);
    }
}