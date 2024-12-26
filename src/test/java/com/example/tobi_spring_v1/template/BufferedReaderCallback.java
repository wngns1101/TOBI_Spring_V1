package com.example.tobi_spring_v1.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public interface BufferedReaderCallback {
    Integer doSomethingWithReader(BufferedReader br) throws IOException;


}
