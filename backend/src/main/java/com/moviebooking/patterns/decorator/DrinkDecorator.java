package com.moviebooking.patterns.decorator;

public class DrinkDecorator extends BookingDecorator {
    public DrinkDecorator(BookingComponent c) { super(c); }
    @Override public double getCost() { return component.getCost() + 150.0; }
    @Override public String getDescription() { return component.getDescription() + " + Drink"; }
}
