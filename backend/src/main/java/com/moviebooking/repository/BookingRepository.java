package com.moviebooking.repository;

import com.moviebooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, String> {
    List<Booking> findByStatusOrderByCreatedAtDesc(String status);
    List<Booking> findAllByOrderByCreatedAtDesc();
}
