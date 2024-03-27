package sg.edu.nus.iss.ibfb4ssfassessment.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.repo.MovieRepo;

@Service
public class DatabaseService {
    @Autowired
    MovieRepo movieRepo;

    // TODO: Task 2 (Save to Redis Map)
    public void saveRecord(Movie movie) {
        movieRepo.createMovie(movie);
    }

    // TODO: Task 3 (Map or List - comment where necesary)
    public long getNumberOfMovies() {
        return movieRepo.getNumberOfMovies();
    }

    public Movie getMovie(Integer index) {
        return movieRepo.getMovie(index);
    }

    public Boolean isMovieIdExist (Integer id){
        return movieRepo.isMovieIdExist(id);
    }

    // TODO: Task 4 (Map)
    public Movie getMovieById(Integer movieId) {
        return movieRepo.getMovie(movieId);
    }

    // TODO: Task 5
    public List<Movie> getAllMovies() {
        return movieRepo.getAllMovies().stream().sorted(Comparator.comparing(Movie::getMovieId)).collect(Collectors.toList());
    }
}
