package com.moviebooking.patterns.decorator;

public class PopcornDecorator extends BookingDecorator {
    public PopcornDecorator(BookingComponent c) { super(c); }
    @Override public double getCost() { return component.getCost() + 250.0; }
    @Override public String getDescription() { return component.getDescription() + " + Popcorn"; }
}
