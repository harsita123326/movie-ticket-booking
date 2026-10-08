package com.moviebooking.patterns.factory;

public class TicketFactory {
    public static Ticket createTicket(String type) {
        if (type == null) return new RegularTicket();
        switch (type.toUpperCase()) {
            case "PREMIUM": return new PremiumTicket();
            case "VIP":     return new VipTicket();
            default:        return new RegularTicket();
        }
    }
}
