package com.moviebooking.patterns.strategy;

public class WalletPayment implements PaymentStrategy {
    @Override public String pay(double a) { return "Paid ₹" + a + " via Wallet"; }
}
