package com.moviebooking.patterns.factory;

public class VipTicket implements Ticket {
    @Override public String getType() { return "VIP"; }
    @Override public double getPrice(double b) { return b * 2.5; }
}
