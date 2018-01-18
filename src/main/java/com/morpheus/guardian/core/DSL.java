package com.morpheus.guardian.core;

import org.hamcrest.Matcher;

import java.util.List;
import java.util.function.Consumer;

public interface DSL<T> {
    <S> Select<S> select(Consumer<Selector<S>> selector);

    <S> Where<S> where(Consumer<Wherer<S>> wherer);

    List<Validator.Error> should(Validator<T> validator);

    interface Selector<T> {
        <T> Select.Selector<T> path(String path);
    }

    interface Select<T> {
        List<Validator.Error> should(Validator<T> validator);

        interface Selector<T> {
            T apply(Object context);
        }
    }

    interface Wherer<S> {
        MatcherBuilder<S> path(String path);

        interface MatcherBuilder<T> {
            Matcher<T> is(T t);

            Matcher<T> not(T t);

            Matcher<T> eq(T t);

            Matcher<T> and(Matcher<T>... matchers);
        }
    }

    interface Where<S> {
        <S> Select<S> select(Consumer<Selector<S>> builder);

        List<Validator.Error> should(Validator<S> validator);
    }
}
