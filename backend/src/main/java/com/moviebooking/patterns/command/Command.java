package com.moviebooking.patterns.command;

public interface Command<T> {
    T execute();
}
