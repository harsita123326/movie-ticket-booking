package com.moviebooking.patterns.decorator;

public abstract class BookingDecorator implements BookingComponent {
    protected final BookingComponent component;
    public BookingDecorator(BookingComponent c) { this.component = c; }
    @Override public double getCost() { return component.getCost(); }
    @Override public String getDescription() { return component.getDescription(); }
}
