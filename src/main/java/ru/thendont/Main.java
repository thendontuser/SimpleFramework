package ru.thendont;

public class Main {
    public static void main(String[] args) {
        TestRunner runner = new TestRunner(TestArithmetical.class);
        runner.run();
    }
}