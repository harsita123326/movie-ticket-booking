package com.moviebooking.patterns.visitor;

import com.moviebooking.entity.Booking;

public interface BookingVisitor<T> {
    T visit(Booking booking);
}
