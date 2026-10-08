package com.moviebooking.patterns.facade;

import com.moviebooking.dto.AdminStats;
import com.moviebooking.entity.Booking;
import com.moviebooking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnalyticsFacade {

    @Autowired
    private BookingRepository bookingRepository;

    public AdminStats computeStats() {
        List<Booking> all = bookingRepository.findAll();

        AdminStats stats = new AdminStats();
        stats.setTotalBookings(all.size());
        stats.setConfirmedBookings(all.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus())).count());
        stats.setCancelledBookings(all.stream()
                .filter(b -> "CANCELLED".equals(b.getStatus())).count());

        stats.setTotalRevenue(all.stream()
                .filter(b -> "CONFIRMED".equals(b.getStatus()))
                .mapToDouble(Booking::getTotalAmount).sum());

        stats.setCancelledRevenue(all.stream()
                .filter(b -> "CANCELLED".equals(b.getStatus()))
                .mapToDouble(Booking::getTotalAmount).sum());

        stats.setBookingsByTicketType(all.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getTicketType() == null ? "UNKNOWN" : b.getTicketType(),
                        Collectors.counting())));

        stats.setBookingsByPayment(all.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getPaymentMethod() == null ? "UNKNOWN" : b.getPaymentMethod(),
                        Collectors.counting())));

        stats.setBookingsByMovie(all.stream()
                .collect(Collectors.groupingBy(
                        b -> b.getMovieTitle() == null ? "UNKNOWN" : b.getMovieTitle(),
                        Collectors.counting())));

        return stats;
    }
}
