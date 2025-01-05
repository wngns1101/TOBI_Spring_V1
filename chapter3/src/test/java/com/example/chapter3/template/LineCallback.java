package com.example.chapter3.template;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
