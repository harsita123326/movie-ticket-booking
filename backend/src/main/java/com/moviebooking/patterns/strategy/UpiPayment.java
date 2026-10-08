package com.moviebooking.patterns.strategy;

public class UpiPayment implements PaymentStrategy {
    @Override public String pay(double a) { return "Paid ₹" + a + " via UPI"; }
}
