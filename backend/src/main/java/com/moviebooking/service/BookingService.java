package com.moviebooking.service;

import com.moviebooking.dto.BookingRequest;
import com.moviebooking.dto.BookingResponse;
import com.moviebooking.entity.Booking;
import com.moviebooking.patterns.builder.BookingBuilder;
import com.moviebooking.patterns.command.CancelBookingCommand;
import com.moviebooking.patterns.decorator.*;
import com.moviebooking.patterns.factory.Ticket;
import com.moviebooking.patterns.factory.TicketFactory;
import com.moviebooking.patterns.observer.*;
import com.moviebooking.patterns.singleton.BookingCounter;
import com.moviebooking.patterns.strategy.PaymentContext;
import com.moviebooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private static final double BASE_PRICE = 200.0;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CancelBookingCommand cancelBookingCommand;

    public BookingResponse book(BookingRequest req) {
        String bookingId = BookingCounter.getInstance().nextBookingId();

        // FACTORY
        Ticket ticket = TicketFactory.createTicket(req.getTicketType());
        double ticketPrice = ticket.getPrice(BASE_PRICE);
        int seats = (req.getSeats() == null || req.getSeats().isEmpty())
                ? 1 : req.getSeats().size();
        double baseCost = ticketPrice * seats;

        // DECORATOR
        BookingComponent component = new BaseBooking(baseCost);
        if (req.getAddOns() != null) {
            for (String addOn : req.getAddOns()) {
                switch (addOn.toUpperCase()) {
                    case "POPCORN": component = new PopcornDecorator(component); break;
                    case "DRINK":   component = new DrinkDecorator(component);   break;
                }
            }
        }
        double totalAmount = component.getCost();

        // STRATEGY
        PaymentContext paymentContext = new PaymentContext(req.getPaymentMethod());
        String paymentResult = paymentContext.executePayment(totalAmount);

        // BUILDER
        Booking booking = new BookingBuilder()
                .withId(bookingId)
                .withMovie(req.getMovieTitle())
                .withShow(req.getShowTime())
                .withTicketType(ticket.getType())
                .withSeats(req.getSeats())
                .withAddOns(req.getAddOns())
                .withAmount(totalAmount)
                .withPaymentMethod(req.getPaymentMethod())
                .withStatus("CONFIRMED")
                .build();
        booking.setUserEmail(req.getUserEmail());
        booking.setUserPhone(req.getUserPhone());

        // Persist to real H2 DB
        bookingRepository.save(booking);

        // OBSERVER
        BookingSubject subject = new BookingSubject();
        subject.register(new EmailNotifier());
        subject.register(new SmsNotifier());

        List<String> notifications = subject.notifyAll(bookingId,
                "Booking confirmed for " + req.getMovieTitle());
        notifications.add(paymentResult);
        notifications.add("🎟️ " + component.getDescription() + " | Total: ₹" + totalAmount);

        return toResponse(booking, notifications);
    }

    public Booking cancelBooking(String bookingId, String reason) {
        return cancelBookingCommand.with(bookingId, reason).execute();
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByCreatedAtDesc();
    }

    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatusOrderByCreatedAtDesc(status);
    }

    public BookingResponse toResponse(Booking b, List<String> notes) {
        BookingResponse res = new BookingResponse();
        res.setBookingId(b.getBookingId());
        res.setMovieTitle(b.getMovieTitle());
        res.setShowTime(b.getShowTime());
        res.setTicketType(b.getTicketType());
        res.setSeats(b.getSeats());
        res.setAddOns(b.getAddOns());
        res.setTotalAmount(b.getTotalAmount());
        res.setPaymentMethod(b.getPaymentMethod());
        res.setStatus(b.getStatus());
        res.setNotifications(notes == null ? new ArrayList<>() : notes);
        return res;
    }
}
