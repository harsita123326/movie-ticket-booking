package com.moviebooking.patterns.singleton;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SINGLETON PATTERN
 * Generates unique booking IDs across the app.
 */
public class BookingCounter {

    private static volatile BookingCounter instance;
    private final AtomicInteger counter = new AtomicInteger(1000);

    private BookingCounter() {}

    public static BookingCounter getInstance() {
        if (instance == null) {
            synchronized (BookingCounter.class) {
                if (instance == null) instance = new BookingCounter();
            }
        }
        return instance;
    }

    public String nextBookingId() {
        return "BK" + counter.incrementAndGet();
    }
}
