package com.moviebooking.patterns.factory;

public interface Ticket {
    String getType();
    double getPrice(double basePrice);
}
