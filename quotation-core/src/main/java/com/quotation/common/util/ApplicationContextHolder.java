package com.quotation.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by yang_shoulai on 8/4/2017.
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHolder.context = applicationContext;
    }

    public static <T> T getBean(Class<T> clz) {
        if (context == null) throw new RuntimeException();
        T t = ApplicationContextHolder.context.getBean(clz);
        return t;
    }
}
