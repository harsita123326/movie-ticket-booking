package com.moviebooking.patterns.factory;

public class RegularTicket implements Ticket {
    @Override public String getType() { return "REGULAR"; }
    @Override public double getPrice(double b) { return b; }
}
