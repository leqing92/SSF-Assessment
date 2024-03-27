package sg.edu.nus.iss.ibfb4ssfassessment.repo;

import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.util.Util;

@Repository
public class MovieRepo {
    
    @Autowired
    @Qualifier(Util.REDIS_ONE)
    RedisTemplate <String, String> template;

//Create
    public void createMovie(Movie movie) {
        HashOperations<String, String, String> hashOps = template.opsForHash();
        hashOps.put(Util.KEY_MOVIE, movie.getMovieId().toString(), movie.toJsonString());        
    }
//Read
    public Boolean isMovieIdExist(Integer id){
        HashOperations<String, String, String> hashOps = template.opsForHash();
        return hashOps.hasKey(Util.KEY_MOVIE, id.toString());
    }

    public long getNumberOfMovies() {
        HashOperations<String, String, String> hashOps = template.opsForHash();
        return hashOps.size(Util.KEY_MOVIE);
    }

    public Movie getMovie(Integer index) {
        HashOperations<String, String, String> hashOps = template.opsForHash();
        return parseMovieFromHardcodedMethod(hashOps.get(Util.KEY_MOVIE, index.toString()));
    }

    public List <Movie> getAllMovies(){
        HashOperations<String, String, String> hashOps = template.opsForHash();
        List<Movie> movies = new LinkedList<>();
        Map <String, String> moviesInMap= hashOps.entries(Util.KEY_MOVIE);        
        
        for (String movieInString : moviesInMap.values()) {
            Movie movie = parseMovieFromHardcodedMethod(movieInString);
            movies.add(movie);
        }
        return movies;
    }
    //for Redis
    public Movie parseMovieFromHardcodedMethod(String movieInString) {
        JsonObject jsonObject = Json.createReader(new StringReader(movieInString)).readObject();        

        Integer movieId = jsonObject.getInt("movieId");
        String title = jsonObject.getString("title");
        String year = jsonObject.getString("year");
        String rated = jsonObject.getString("rated");
        Long releaseDate = jsonObject.getJsonNumber("releaseDate").longValue();      
        String runTime = jsonObject.getString("runTime");
        String genre = jsonObject.getString("genre");
        String director = jsonObject.getString("director");
        Double rating = jsonObject.getJsonNumber("rating").doubleValue();
        Integer count = jsonObject.getInt("count");

        return new Movie(movieId, title, year, rated, releaseDate, runTime, genre, director, rating, count);
    }

}
