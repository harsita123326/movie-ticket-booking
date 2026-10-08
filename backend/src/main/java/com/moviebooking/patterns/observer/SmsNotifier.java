package com.moviebooking.patterns.observer;

public class SmsNotifier implements BookingObserver {
    @Override public String notify(String id, String m) {
        return "📱 SMS sent for " + id + ": " + m;
    }
}
