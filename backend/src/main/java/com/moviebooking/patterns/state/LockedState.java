package com.moviebooking.patterns.state;

public class LockedState implements SeatState {
    @Override public String getStateName() { return "LOCKED"; }
    @Override public boolean isBookable() { return false; }
}
