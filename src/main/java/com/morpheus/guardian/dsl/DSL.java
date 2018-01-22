package com.morpheus.guardian.dsl;

import com.morpheus.guardian.core.Validator;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

import java.util.List;
import java.util.function.Consumer;

public class DSL<T> implements com.morpheus.guardian.core.DSL<T> {
    private Object context;

    public DSL(Object context) {
        this.context = context;
    }

    public static <T> com.morpheus.guardian.core.DSL<T> from(Object context) {
        if (context == null) {
            return new Nil<>(new Validatable<T>(context, context1 -> null));
        }

        return new DSL<>(context);
    }

    @Override
    public <S> com.morpheus.guardian.core.DSL.Select<S> select(Consumer<com.morpheus.guardian.core.DSL.Selector<S>> selector) {
        Selector<S> selectorBuilder = new Selector<>(context);
        selector.accept(selectorBuilder);

        return new Select<>(context, selectorBuilder.build());
    }

    @Override
    public <S> com.morpheus.guardian.core.DSL.Where<S> where(Consumer<com.morpheus.guardian.core.DSL.Filter<S>> filter) {
        Filter<S> t = new Filter<>(context);
        filter.accept(t);

        return new Where<>(context, t.build());
    }

    @Override
    public List<Validator.Error> should(Validator<T> validator) {
        return validator.validate(new Validatable<>(context, context -> (T) context));
    }

    public class Filter<S> implements com.morpheus.guardian.core.DSL.Filter<S> {
        public Filter(Object context) {

        }

        private Matcher<S> build() {
            return null;
        }

        @Override
        public com.morpheus.guardian.core.DSL.MatcherBuilder<S> path(String path) {
            return new MatcherBuilder<>();
        }

    }

    public class Where<S> implements com.morpheus.guardian.core.DSL.Where<S> {
        public Where(Object context, Matcher<S> matcher) {
        }


        @Override
        public <S> com.morpheus.guardian.core.DSL.Select<S> select(Consumer<com.morpheus.guardian.core.DSL.Selector<S>> builder) {
            Selector<S> s1SelectorBuilder = new Selector<>(context);
            builder.accept(s1SelectorBuilder);

            return new Select<>(context, s1SelectorBuilder.build());
        }

        @Override
        public List<Validator.Error> should(Validator<S> validator) {
            return null;
        }
    }

    public static class Select<T> implements com.morpheus.guardian.core.DSL.Select<T> {
        private final Object context;
        private final Selector<T> value;

        public Select(Object context, Selector<T> value) {
            this.context = context;
            this.value = value;
        }

        @Override
        public List<Validator.Error> should(Validator<T> validator) {
            return validator.validate(new Validatable<>(context, context -> null));
        }

        @Override
        public T value() {
            return null;
        }
    }

    public static class Selector<S> implements com.morpheus.guardian.core.DSL.Selector<S> {
        public Selector(Object context) {

        }

        @Override
        public <T> com.morpheus.guardian.core.DSL.Select.Selector<T> path(String path) {
            return context -> null;
        }

        private com.morpheus.guardian.core.DSL.Select.Selector<S> build() {
            return null;
        }
    }

    public class MatcherBuilder<T> implements com.morpheus.guardian.core.DSL.MatcherBuilder<T> {
        @Override
        public Matcher<T> is(T t) {
            return CoreMatchers.is(t);
        }

        @Override
        public Matcher<T> not(T t) {
            return CoreMatchers.not(t);
        }

        @Override
        public Matcher<T> eq(T t) {
            return CoreMatchers.equalTo(t);
        }

        @Override
        public Matcher<T> and(Matcher<T>... matchers) {
            return CoreMatchers.allOf(matchers);
        }
    }
}
