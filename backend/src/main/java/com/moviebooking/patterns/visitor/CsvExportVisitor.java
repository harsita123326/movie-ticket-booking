package com.moviebooking.patterns.visitor;

import com.moviebooking.entity.Booking;

public class CsvExportVisitor implements BookingVisitor<String> {

    @Override
    public String visit(Booking b) {
        return String.join(",",
                escape(b.getBookingId()),
                escape(b.getMovieTitle()),
                escape(b.getShowTime()),
                escape(b.getTicketType()),
                escape(b.getSeats() == null ? "" : String.join("|", b.getSeats())),
                escape(b.getAddOns() == null ? "" : String.join("|", b.getAddOns())),
                String.valueOf(b.getTotalAmount()),
                escape(b.getPaymentMethod()),
                escape(b.getStatus()),
                escape(b.getUserEmail()),
                escape(b.getUserPhone()),
                b.getCreatedAt() == null ? "" : b.getCreatedAt().toString()
        );
    }

    public static String header() {
        return "BookingId,Movie,Show,TicketType,Seats,AddOns,Amount,Payment,Status,Email,Phone,CreatedAt";
    }

    private String escape(String s) {
        if (s == null) return "";
        if (s.contains(",") || s.contains("\"")) return "\"" + s.replace("\"", "\"\"") + "\"";
        return s;
    }
}
