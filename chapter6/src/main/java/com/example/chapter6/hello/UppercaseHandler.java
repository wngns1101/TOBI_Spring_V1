package com.example.chapter6.hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {
    Object target;

    public UppercaseHandler(Hello hello) {
        this.target = hello;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String ret = (String) method.invoke(target, args);

        if (ret instanceof String) {
            return (String) ret.toUpperCase();
        }

        return ret;
    }
}
