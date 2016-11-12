package com.sem3.lab5;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Functional.findTags();
            Functional.findWords();
        } catch (IOException e) {
            System.out.println("File not found!");
        }
    }
}
