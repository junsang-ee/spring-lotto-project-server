package com.lotto.web.util;

import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;

import static org.springframework.beans.factory.BeanFactoryUtils.beanOfTypeIncludingAncestors;
import static lombok.AccessLevel.PRIVATE;
@NoArgsConstructor(access = PRIVATE)
public final class BeanSuppliers {

    public static <T> SingletonSupplier<T> beanSupplier(ApplicationContext beanFactory, Class<T> type) {
        return SingletonSupplier.of(() -> beanOfTypeIncludingAncestors(beanFactory, type));
    }

}
