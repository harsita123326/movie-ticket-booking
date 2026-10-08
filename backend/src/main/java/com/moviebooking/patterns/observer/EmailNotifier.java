package com.moviebooking.patterns.observer;

public class EmailNotifier implements BookingObserver {
    @Override public String notify(String id, String m) {
        return "📧 Email sent for " + id + ": " + m;
    }
}
