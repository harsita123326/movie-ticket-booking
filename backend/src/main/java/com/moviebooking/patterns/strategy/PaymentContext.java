package com.moviebooking.patterns.strategy;

public class PaymentContext {
    private final PaymentStrategy strategy;

    public PaymentContext(String method) {
        if (method == null) strategy = new CreditCardPayment();
        else switch (method.toUpperCase()) {
            case "UPI":    strategy = new UpiPayment(); break;
            case "WALLET": strategy = new WalletPayment(); break;
            default:       strategy = new CreditCardPayment();
        }
    }

    public String executePayment(double amount) {
        return strategy.pay(amount);
    }
}
