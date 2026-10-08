package com.moviebooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    @Column(name = "show_time")
    private String time;

    @Column(name = "base_price")
    private double basePrice;

    public Show() {}

    public Show(Long movieId, String time, double basePrice) {
        this.movieId = movieId;
        this.time = time;
        this.basePrice = basePrice;
    }

    public Long getId() { return id; }
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long v) { this.movieId = v; }
    public String getTime() { return time; }
    public void setTime(String v) { this.time = v; }
    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double v) { this.basePrice = v; }
}
