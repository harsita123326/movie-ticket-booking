package com.moviebooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String language;
    private String genre;

    @Column(name = "base_price")
    private double basePrice;

    private boolean active = true;

    public Movie() {}

    public Movie(String title, String language, String genre, double basePrice, boolean active) {
        this.title = title;
        this.language = language;
        this.genre = genre;
        this.basePrice = basePrice;
        this.active = active;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String v) { this.title = v; }
    public String getLanguage() { return language; }
    public void setLanguage(String v) { this.language = v; }
    public String getGenre() { return genre; }
    public void setGenre(String v) { this.genre = v; }
    public double getBasePrice() { return basePrice; }
    public void setBasePrice(double v) { this.basePrice = v; }
    public boolean isActive() { return active; }
    public void setActive(boolean v) { this.active = v; }
}
