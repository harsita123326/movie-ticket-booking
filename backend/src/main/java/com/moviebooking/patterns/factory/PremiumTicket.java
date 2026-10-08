package com.moviebooking.patterns.factory;

public class PremiumTicket implements Ticket {
    @Override public String getType() { return "PREMIUM"; }
    @Override public double getPrice(double b) { return b * 1.5; }
}
