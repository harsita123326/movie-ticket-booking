package com.moviebooking.dto;

import java.util.Map;

public class AdminStats {
    private long totalBookings;
    private long confirmedBookings;
    private long cancelledBookings;
    private double totalRevenue;
    private double cancelledRevenue;
    private Map<String, Long> bookingsByTicketType;
    private Map<String, Long> bookingsByPayment;
    private Map<String, Long> bookingsByMovie;

    public long getTotalBookings() { return totalBookings; }
    public void setTotalBookings(long v) { this.totalBookings = v; }
    public long getConfirmedBookings() { return confirmedBookings; }
    public void setConfirmedBookings(long v) { this.confirmedBookings = v; }
    public long getCancelledBookings() { return cancelledBookings; }
    public void setCancelledBookings(long v) { this.cancelledBookings = v; }
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double v) { this.totalRevenue = v; }
    public double getCancelledRevenue() { return cancelledRevenue; }
    public void setCancelledRevenue(double v) { this.cancelledRevenue = v; }
    public Map<String, Long> getBookingsByTicketType() { return bookingsByTicketType; }
    public void setBookingsByTicketType(Map<String, Long> v) { this.bookingsByTicketType = v; }
    public Map<String, Long> getBookingsByPayment() { return bookingsByPayment; }
    public void setBookingsByPayment(Map<String, Long> v) { this.bookingsByPayment = v; }
    public Map<String, Long> getBookingsByMovie() { return bookingsByMovie; }
    public void setBookingsByMovie(Map<String, Long> v) { this.bookingsByMovie = v; }
}
