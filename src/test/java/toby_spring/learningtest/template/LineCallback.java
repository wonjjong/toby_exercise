package toby_spring.learningtest.template;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
