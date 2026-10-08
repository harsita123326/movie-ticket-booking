package com.moviebooking.patterns.state;

public class BookedState implements SeatState {
    @Override public String getStateName() { return "BOOKED"; }
    @Override public boolean isBookable() { return false; }
}
