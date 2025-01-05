package com.example.chapter4.template;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
