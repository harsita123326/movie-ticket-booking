package com.moviebooking.controller;

import com.moviebooking.dto.BookingRequest;
import com.moviebooking.dto.BookingResponse;
import com.moviebooking.entity.Movie;
import com.moviebooking.entity.Show;
import com.moviebooking.repository.MovieRepository;
import com.moviebooking.repository.ShowRepository;
import com.moviebooking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookingController {

    @Autowired private BookingService bookingService;
    @Autowired private MovieRepository movieRepository;
    @Autowired private ShowRepository showRepository;

    @GetMapping("/movies")
    public List<Movie> getMovies() {
        return movieRepository.findByActiveTrue();
    }

    @GetMapping("/shows/{movieId}")
    public List<Show> getShows(@PathVariable Long movieId) {
        return showRepository.findByMovieId(movieId);
    }

    @PostMapping("/book")
    public BookingResponse book(@RequestBody BookingRequest request) {
        return bookingService.book(request);
    }
}
