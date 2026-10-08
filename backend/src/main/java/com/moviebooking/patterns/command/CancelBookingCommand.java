package com.moviebooking.patterns.command;

import com.moviebooking.entity.Booking;
import com.moviebooking.patterns.observer.*;
import com.moviebooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CancelBookingCommand implements Command<Booking> {

    @Autowired
    private BookingRepository bookingRepository;

    private String bookingId;
    private String reason;

    public CancelBookingCommand() {}

    public CancelBookingCommand with(String bookingId, String reason) {
        this.bookingId = bookingId;
        this.reason = reason;
        return this;
    }

    @Override
    public Booking execute() {
        Booking b = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        if ("CANCELLED".equals(b.getStatus()))
            throw new RuntimeException("Booking already cancelled");

        b.setStatus("CANCELLED");
        b.setCancelledReason(reason == null ? "No reason" : reason);
        bookingRepository.save(b);

        BookingSubject subject = new BookingSubject();
        subject.register(new EmailNotifier());
        subject.register(new SmsNotifier());
        List<String> notes = subject.notifyAll(bookingId,
                "Booking CANCELLED. Reason: " + b.getCancelledReason());
        System.out.println("[CANCEL] " + notes);

        return b;
    }
}
