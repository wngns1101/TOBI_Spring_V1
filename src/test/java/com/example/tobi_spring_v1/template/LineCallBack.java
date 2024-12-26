package com.example.tobi_spring_v1.template;

public interface LineCallBack<T> {
    T doSomethingWithLine(String line, T value);
}
