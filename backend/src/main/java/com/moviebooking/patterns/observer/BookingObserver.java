package com.moviebooking.patterns.observer;

public interface BookingObserver {
    String notify(String bookingId, String message);
}
