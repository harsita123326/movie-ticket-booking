package com.moviebooking.patterns.state;

public interface SeatState {
    String getStateName();
    boolean isBookable();
}
