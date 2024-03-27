package sg.edu.nus.iss.ibfb4ssfassessment.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;

@Service
public class FileService{
       
    @Autowired
    DatabaseService movieSvc;

    // TODO: Task 1
    public List<Movie> readFile(String fileName) {
        StringBuilder output = new StringBuilder();
        File file = new File(fileName);
        List <Movie> movies = new LinkedList<>();

        if(file.exists()){
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
        
                while ((line = br.readLine()) != null) {
                    output.append(line);
                }                    
                
            }catch(IOException e){
                System.out.println("IOException: " + e.getMessage());
            }
        }else{
            System.out.println("file not exist: " + fileName);
        }

        try (InputStream is = new ByteArrayInputStream(output.toString().getBytes())) {
			JsonReader reader = Json.createReader(is);
			JsonArray jArray = reader.readArray();
			
			for(int i = 0; i < jArray.size(); i++){
                String movieInString = jArray.get(i).toString();
                Movie movie = parseMovieFromHardcodedMethod(movieInString);                
                movies.add(movie);                
            }
		}catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        } 
		
		System.out.println("Data saved to Redis successfully.");

        return movies;
    }
    //for the movies.json file
    public Movie parseMovieFromHardcodedMethod(String movieInString) {
        JsonObject jsonObject = Json.createReader(new StringReader(movieInString)).readObject();        

        Integer movieId = jsonObject.getInt("Id");
        String title = jsonObject.getString("Title");
        String year = jsonObject.getString("Year");
        String rated = jsonObject.getString("Rated");
        Long releaseDate = jsonObject.getJsonNumber("Released").longValue();    
        String runTime = jsonObject.getString("Runtime");
        String genre = jsonObject.getString("Genre");
        String director = jsonObject.getString("Director");
        Double rating = jsonObject.getJsonNumber("Rating").doubleValue();
        Integer count = jsonObject.getInt("Count");

        return new Movie(movieId, title, year, rated, releaseDate, runTime, genre, director, rating, count);
    }

}
