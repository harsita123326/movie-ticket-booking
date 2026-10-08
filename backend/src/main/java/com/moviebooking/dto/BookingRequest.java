package com.moviebooking.dto;

import java.util.List;

public class BookingRequest {
    private String movieTitle;
    private String showTime;
    private String ticketType;
    private List<String> seats;
    private List<String> addOns;
    private String paymentMethod;
    private String userEmail;
    private String userPhone;

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
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String v) { this.paymentMethod = v; }
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String v) { this.userEmail = v; }
    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String v) { this.userPhone = v; }
}
