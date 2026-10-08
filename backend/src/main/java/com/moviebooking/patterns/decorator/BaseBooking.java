package com.moviebooking.patterns.decorator;

public class BaseBooking implements BookingComponent {
    private final double basePrice;
    public BaseBooking(double b) { this.basePrice = b; }
    @Override public double getCost() { return basePrice; }
    @Override public String getDescription() { return "Base Ticket"; }
}
