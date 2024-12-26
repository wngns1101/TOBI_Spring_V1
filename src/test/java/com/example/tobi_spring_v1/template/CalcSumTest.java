package com.example.tobi_spring_v1.template;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

public class CalcSumTest {
    Calculator calculator;
    String numFilePath;

    @BeforeEach
    public void setUp() {
        this.calculator = new Calculator();
        this.numFilePath = "/Users/juhoonlee/Desktop/TOBI_Spring_V1/src/test/java/com/example/tobi_spring_v1/template/numbers.txt";
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(this.numFilePath)).isEqualTo(10);
    }

    @Test
    public void multiplyNumbers() throws IOException {
        assertThat(calculator.calcMultiply(this.numFilePath)).isEqualTo(24);
    }

    @Test
    public void concatenateString() throws IOException {
        assertThat(calculator.concatenate(this.numFilePath)).isEqualTo("1234");
    }
}
