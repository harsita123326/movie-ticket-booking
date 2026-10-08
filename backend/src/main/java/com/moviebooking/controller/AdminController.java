package com.moviebooking.controller;

import com.moviebooking.dto.AdminLoginRequest;
import com.moviebooking.dto.AdminStats;
import com.moviebooking.dto.BookingResponse;
import com.moviebooking.entity.Booking;
import com.moviebooking.patterns.facade.AnalyticsFacade;
import com.moviebooking.patterns.visitor.CsvExportVisitor;
import com.moviebooking.repository.BookingRepository;
import com.moviebooking.service.AdminService;
import com.moviebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired private BookingService bookingService;
    @Autowired private AdminService adminService;
    @Autowired private AnalyticsFacade analyticsFacade;
    @Autowired private BookingRepository bookingRepository;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AdminLoginRequest req) {
        return adminService.login(req);
    }

    @GetMapping("/stats")
    public AdminStats stats() {
        return analyticsFacade.computeStats();
    }

    @GetMapping("/bookings")
    public List<BookingResponse> getAllBookings(
            @RequestParam(required = false) String status) {

        List<Booking> list = (status == null || status.isBlank())
                ? bookingService.getAllBookings()
                : bookingService.getBookingsByStatus(status);

        return list.stream()
                .map(b -> bookingService.toResponse(b, null))
                .collect(Collectors.toList());
    }

    @PostMapping("/bookings/{id}/cancel")
    public BookingResponse cancel(@PathVariable String id,
                                  @RequestParam(required = false) String reason) {
        Booking b = bookingService.cancelBooking(id, reason);
        return bookingService.toResponse(b, null);
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportCsv() {
        CsvExportVisitor visitor = new CsvExportVisitor();
        StringBuilder csv = new StringBuilder();
        csv.append(CsvExportVisitor.header()).append("\n");
        for (Booking b : bookingRepository.findAll()) {
            csv.append(visitor.visit(b)).append("\n");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"bookings.csv\"");
        return ResponseEntity.ok().headers(headers).body(csv.toString());
    }

    @PostMapping("/reset")
    public Map<String, Object> reset() {
        bookingRepository.deleteAll();
        return Map.of("success", true, "message", "All bookings cleared");
    }
}
