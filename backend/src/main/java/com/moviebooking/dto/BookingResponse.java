package com.moviebooking.dto;

import java.util.List;

public class BookingResponse {
    private String bookingId;
    private String movieTitle;
    private String showTime;
    private String ticketType;
    private List<String> seats;
    private List<String> addOns;
    private double totalAmount;
    private String paymentMethod;
    private String status;
    private List<String> notifications;

    public String getBookingId() { return bookingId; }
    public void setBookingId(String v) { this.bookingId = v; }
    public String getMovieTitle() { return movieTitle; }
    public void setMovieTitle(String v) { this.movieTitle = v; }
    public String getShowTime() { return showTime; }
    public void setShowTime(String v) { this.showTime = v; }
    public String getTicketType() { return ticketType; }
    public void setTicketType(String v) { this.ticketType = v; }
    public List<String> getSeats() { return seats; }
    public void setSeats(List<String> v) { this.seats = v; }
    public List<String> getAddOns() { return addOns; }
    public void setAddOns(List<String> v) { this.addOns = v; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double v) { this.totalAmount = v; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String v) { this.paymentMethod = v; }
    public String getStatus() { return status; }
    public void setStatus(String v) { this.status = v; }
    public List<String> getNotifications() { return notifications; }
    public void setNotifications(List<String> v) { this.notifications = v; }
}
