package com.moviebooking.patterns.strategy;

public class CreditCardPayment implements PaymentStrategy {
    @Override public String pay(double a) { return "Paid ₹" + a + " via Credit Card"; }
}
