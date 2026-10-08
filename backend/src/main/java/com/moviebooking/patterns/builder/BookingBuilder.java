package com.moviebooking.patterns.builder;

import com.moviebooking.entity.Booking;
import java.util.List;

public class BookingBuilder {
    private final Booking b = new Booking();

    public BookingBuilder withId(String v) { b.setBookingId(v); return this; }
    public BookingBuilder withMovie(String v) { b.setMovieTitle(v); return this; }
    public BookingBuilder withShow(String v) { b.setShowTime(v); return this; }
    public BookingBuilder withTicketType(String v) { b.setTicketType(v); return this; }
    public BookingBuilder withSeats(List<String> v) { b.setSeats(v); return this; }
    public BookingBuilder withAddOns(List<String> v) { b.setAddOns(v); return this; }
    public BookingBuilder withAmount(double v) { b.setTotalAmount(v); return this; }
    public BookingBuilder withPaymentMethod(String v) { b.setPaymentMethod(v); return this; }
    public BookingBuilder withStatus(String v) { b.setStatus(v); return this; }
    public Booking build() { return b; }
}
