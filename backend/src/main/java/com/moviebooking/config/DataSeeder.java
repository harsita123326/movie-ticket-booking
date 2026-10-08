package com.moviebooking.config;

import com.moviebooking.entity.AdminUser;
import com.moviebooking.entity.Movie;
import com.moviebooking.entity.Show;
import com.moviebooking.repository.AdminUserRepository;
import com.moviebooking.repository.MovieRepository;
import com.moviebooking.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private MovieRepository movieRepository;
    @Autowired private ShowRepository showRepository;
    @Autowired private AdminUserRepository adminUserRepository;

    @Override
    public void run(String... args) {
        if (movieRepository.count() == 0) {
            Movie inception = movieRepository.save(
                    new Movie("Inception", "English", "Sci-Fi", 250.0, true));
            Movie interstellar = movieRepository.save(
                    new Movie("Interstellar", "English", "Sci-Fi", 280.0, true));
            Movie idiots = movieRepository.save(
                    new Movie("3 Idiots", "Hindi", "Comedy", 200.0, true));
            Movie baahubali = movieRepository.save(
                    new Movie("Baahubali", "Telugu", "Action", 300.0, true));

            List<Movie> movies = List.of(inception, interstellar, idiots, baahubali);
            String[] times = {"10:00 AM", "02:00 PM", "06:00 PM", "09:30 PM"};
            double[] prices = {200.0, 250.0, 300.0, 350.0};

            for (Movie m : movies) {
                for (int i = 0; i < times.length; i++) {
                    showRepository.save(new Show(m.getId(), times[i], prices[i]));
                }
            }
        }

        if (adminUserRepository.count() == 0) {
            adminUserRepository.save(new AdminUser("admin", "admin123", "ADMIN"));
            adminUserRepository.save(new AdminUser("manager", "manager123", "MANAGER"));
        }
    }
}
