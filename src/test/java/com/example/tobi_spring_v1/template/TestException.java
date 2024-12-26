package com.example.tobi_spring_v1.template;

public class TestException {
    public static void main(String[] args) {
        method1();
    }

    private static void method1() {
        String str = null;
        if (str == null) {
            new Exception();
        }
    }
}
