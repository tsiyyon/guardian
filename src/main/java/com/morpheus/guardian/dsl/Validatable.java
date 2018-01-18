package com.morpheus.guardian.dsl;

import com.morpheus.guardian.core.*;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class Validatable<T> implements com.morpheus.guardian.core.Validatable<T> {
    private Object context;
    private Expression<T> expression;
    private String filter;
    private String name;

    private Validatable(Object context, String filter) {
        this.context = context;
        this.filter = filter;
        this.name = filter.replaceAll(".", "");
    }

    public Validatable(Object context, Expression<T> expression) {
        this.context = context;
        this.expression = expression;
    }

    @Override
    public Validatable<T> field(String expression) {
        return new Validatable<>(context, expression);
    }

    @Override
    public T value() {
        if (Map.class.isAssignableFrom(context.getClass())) {
            return (T) ((Map) context).get("filter");
        }
        return null;
    }

    String pointer() {
        return filter;
    }

    String name() {
        return name;
    }

    public static <T> com.morpheus.guardian.core.DSL<T> from(Object context) {
        return new DSL<>(context);
    }

    @Override
    public List<Validator.Error> should(Validator<T> validator) {
        return validator.validate(this);
    }

    public interface Expression<R> {
        R applied(Object context);
    }

    public static class DSL<T> implements com.morpheus.guardian.core.DSL<T> {
        private Object context;

        public DSL(Object context) {
            this.context = context;
        }

        @Override
        public <S> Select<S> select(Consumer<Selector<S>> selector) {
            Validatable.Selector<S> selectorBuilder = new Validatable.Selector<>(context);
            selector.accept(selectorBuilder);

            return new Validatable.Select<>(context, selectorBuilder.build());
        }

        @Override
        public <S> com.morpheus.guardian.core.DSL.Where<S> where(Consumer<com.morpheus.guardian.core.DSL.Wherer<S>> wherer) {
            Wherer<S> t = new Wherer<>(context);
            wherer.accept(t);

            return new Where<>(context, t.build());
        }

        @Override
        public List<Validator.Error> should(Validator<T> validator) {
            return validator.validate(new Validatable<>(context, context -> null));
        }

        public class Wherer<S> implements com.morpheus.guardian.core.DSL.Wherer<S> {
            public Wherer(Object context) {

            }

            private Matcher<S> build() {
                return null;
            }

            @Override
            public MatcherBuilder<S> path(String path) {
                return new MatcherBuilder<>();
            }

            public class MatcherBuilder<T> implements com.morpheus.guardian.core.DSL.Wherer.MatcherBuilder<T> {
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

        public class Where<S> implements com.morpheus.guardian.core.DSL.Where<S> {
            public Where(Object context, Matcher<S> matcher) {
            }


            @Override
            public <S1> Select<S1> select(Consumer<Selector<S1>> builder) {
                Validatable.Selector<S1> s1SelectorBuilder = new Validatable.Selector<>(context);
                builder.accept(s1SelectorBuilder);

                return new Validatable.Select<>(context, s1SelectorBuilder.build());
            }

            @Override
            public List<Validator.Error> should(Validator<S> validator) {
                return null;
            }
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
    }

    public static class Selector<S> implements com.morpheus.guardian.core.DSL.Selector<S> {
        public Selector(Object context) {

        }

        @Override
        public <T> com.morpheus.guardian.core.DSL.Select.Selector<T> path(String path) {
            return new com.morpheus.guardian.core.DSL.Select.Selector<T>() {
                @Override
                public T apply(Object context) {
                    return null;
                }
            };
        }

        private com.morpheus.guardian.core.DSL.Select.Selector<S> build() {
            return null;
        }
    }
}
