package com.lotto.web.config.dsl;


import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.SimpleExpression;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.Optional.ofNullable;

public abstract class Criteria {

    private static <T> Predicate optional(T value, Function<T, Predicate> func) {
        return ofNullable(value).map(func).orElse(null);
    }


    protected static <T> Predicate eq(@NonNull SimpleExpression<T> expression, T value) {
        return optional(value, expression::eq);
    }

    protected static <T> Predicate ne(@NonNull SimpleExpression<T> expression, T value) {
        return optional(value, expression::ne);
    }

    public final Predicate build() {
        PredicateBuilder builder = new PredicateBuilder();
        build(builder);
        return builder.build();
    }
    protected abstract void build(PredicateBuilder predicates);

    protected static class PredicateBuilder {
        private final List<Predicate> andPredicates = new ArrayList<>();
        private final List<Predicate> orPredicates = new ArrayList();


        public PredicateBuilder and(Predicate predicate) {
            ofNullable(predicate).ifPresent(andPredicates::add);
            return this;
        }

        public PredicateBuilder or(Predicate predicate) {
            ofNullable(predicate).ifPresent(orPredicates::add);
            return this;
        }

        public Predicate build() {
            Predicate andPredicate = ExpressionUtils.allOf(andPredicates);
            Predicate orPredicate = ExpressionUtils.anyOf(orPredicates);
            return ExpressionUtils.and(andPredicate, orPredicate);
        }
    }


}
