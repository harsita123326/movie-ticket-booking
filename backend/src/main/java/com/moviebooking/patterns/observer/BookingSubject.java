package com.moviebooking.patterns.observer;

import java.util.ArrayList;
import java.util.List;

public class BookingSubject {
    private final List<BookingObserver> observers = new ArrayList<>();

    public void register(BookingObserver o) { observers.add(o); }

    public List<String> notifyAll(String id, String msg) {
        List<String> results = new ArrayList<>();
        for (BookingObserver o : observers) results.add(o.notify(id, msg));
        return results;
    }
}
