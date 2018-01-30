package com.morpheus.guardian.dsl;

import com.morpheus.guardian.core.Validator;
import com.morpheus.guardian.expressions.JsonPathAddressable;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

import java.util.List;
import java.util.function.Consumer;

import static com.morpheus.guardian.expressions.JsonPathAddressable.jpath;

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
    public <S> com.morpheus.guardian.core.DSL.Select<S> select(Consumer<com.morpheus.guardian.core.DSL.Selector<S>> consumer) {
        Selector<S> selector = new Selector<>(context);
        consumer.accept(selector);

        return new Select<>(context, selector.build());
    }

    @Override
    public <S> com.morpheus.guardian.core.DSL.Where<S> where(Consumer<com.morpheus.guardian.core.DSL.Filter<S>> consumer) {
        Filter<S> filter = new Filter<>(context);
        consumer.accept(filter);

        return new Where<>(context, filter.build());
    }

    @Override
    public List<Validator.Error> should(Validator<T> validator) {
        return validator.validate(new Validatable<>(context, context -> (T) context));
    }

    public class Filter<S> implements com.morpheus.guardian.core.DSL.Filter<S> {
        private Object context;

        public Filter(Object context) {
            this.context = context;
        }

        private Matcher<S> build() {
            return null;
        }

        @Override
        public com.morpheus.guardian.core.DSL.MatcherBuilder<S> path(String path) {
            return new MatcherBuilder<>(path);
        }
    }

    public class Where<S> implements com.morpheus.guardian.core.DSL.Where<S> {
        private final Object context;
        private final Matcher<S> matcher;

        public Where(Object context, Matcher<S> matcher) {
            this.context = context;
            this.matcher = matcher;
        }


        @Override
        public <S> com.morpheus.guardian.core.DSL.Select<S> select(Consumer<com.morpheus.guardian.core.DSL.Selector<S>> consumer) {
            Selector<S> selector = new Selector<>(context);
            consumer.accept(selector);

            return new Select<>(context, selector.build());
        }

        @Override
        public List<Validator.Error> should(Validator<S> validator) {
            // should find field that matched matcher
            // should all the matched field matched the validator
            return null;
        }
    }

    public static class Select<T> implements com.morpheus.guardian.core.DSL.Select<T> {
        private final Object context;
        private final Selector<T> selector;

        public Select(Object context, Selector<T> selector) {
            this.context = context;
            this.selector = selector;
        }

        @Override
        public List<Validator.Error> should(Validator<T> validator) {
            return validator.validate(new Validatable<>(context, selector::apply));
        }

        @Override
        public T value() {
            return selector.apply(context);
        }
    }

    public static class Selector<S> implements com.morpheus.guardian.core.DSL.Selector<S> {
        private Object context;
        private String path;

        public Selector(Object context) {
            this.context = context;
        }

        @Override
        public com.morpheus.guardian.core.DSL.Selector<S> path(String path) {
            this.path = path;
            return this;
        }

        private com.morpheus.guardian.core.DSL.Select.Selector<S> build() {
            return JsonPathAddressable.<S>jpath(path)::locate;
        }
    }

    public class MatcherBuilder<T> implements com.morpheus.guardian.core.DSL.MatcherBuilder<T> {
        public MatcherBuilder(String path) {

        }

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
