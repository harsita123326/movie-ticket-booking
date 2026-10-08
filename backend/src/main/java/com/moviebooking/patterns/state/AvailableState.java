package com.moviebooking.patterns.state;

public class AvailableState implements SeatState {
    @Override public String getStateName() { return "AVAILABLE"; }
    @Override public boolean isBookable() { return true; }
}
