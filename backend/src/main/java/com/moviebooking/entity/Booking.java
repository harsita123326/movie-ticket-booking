package com.moviebooking.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    private String bookingId;

    private String movieTitle;
    private String showTime;
    private String ticketType;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "booking_seats",
            joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "seat")
    private List<String> seats = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "booking_addons",
            joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "addon")
    private List<String> addOns = new ArrayList<>();

    private double totalAmount;
    private String paymentMethod;
    private String status;
    private String userEmail;
    private String userPhone;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private String cancelledReason;

    public Booking() {
        this.createdAt = LocalDateTime.now();
    }

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
    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String v) { this.userEmail = v; }
    public String getUserPhone() { return userPhone; }
    public void setUserPhone(String v) { this.userPhone = v; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime v) { this.createdAt = v; }
    public String getCancelledReason() { return cancelledReason; }
    public void setCancelledReason(String v) { this.cancelledReason = v; }
}
