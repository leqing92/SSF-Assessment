package sg.edu.nus.iss.ibfb4ssfassessment.controller;

import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Login;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.service.DatabaseService;

@Controller
@RequestMapping (path = "/movies")
public class MovieController {

    @Autowired
    DatabaseService movieSvc;

    // TODO: Task 8
    @GetMapping (path = "/list")
    public String displayMovies(HttpSession session, Model model) {
        Login login = (Login) session.getAttribute("login");
        if(null != login){
            List <Movie> movies = movieSvc.getAllMovies();
            model.addAttribute("movies", movies);
            return "view2";
        }
        else{
            return "redirect:/";
        }
    }

    // TODO: Task 9
    @GetMapping(path = "/book/{id}")
    public String bookMovie(HttpSession session,
                            @PathVariable ("id") String id,
                            Model model){

        Login login = (Login) session.getAttribute("login");
        if(null == login){
            return "redirect:/";
        }

        if(!movieSvc.isMovieIdExist(Integer.parseInt(id))){
            return "idnotfound";
        }

        Integer age = ageCal(login.getBirthDate());
        Movie movie = movieSvc.getMovieById(Integer.parseInt(id));
        if(age >= 18){
            model.addAttribute("title", movie.getTitle());
            movie.setCount(movie.getCount() + 1);
            movieSvc.saveRecord(movie);
            // System.out.println(movie.toJsonString());
            return "BookSuccess";
        }
        else if(age >= 13 && "PG-13".equals(movie.getRated())){
            model.addAttribute("title", movie.getTitle());
            movie.setCount(movie.getCount() + 1);
            movieSvc.saveRecord(movie);
            // System.out.println(movie.toJsonString());
            return "BookSuccess";
        }
        else{
            if(age >= 13){
                model.addAttribute("msg", "Age must be equals or greater than 18 years old to be able to book R rated movie");
            }
            else{
                model.addAttribute("msg", "Age must be equals or greater than 13 years old to be able to book PG-13 rated Movie and equals or greater than 18 to be able to book R rated movie");
            }
            return "BookError";
        }
    }

    // TODO: Task 9
    // ... ...
    @GetMapping(path = "/list/{id}")
    public String getMovieById(@PathVariable ("id") String id, Model model){
        if(!movieSvc.isMovieIdExist(Integer.parseInt(id))){
            return "idnotfound";
        }
        model.addAttribute("movie", movieSvc.getMovieById(Integer.parseInt(id)));
        return "movie";
    }
    //https://stackoverflow.com/questions/64883505/calculate-age-between-two-java-util-date
    private Integer ageCal(Date age){
        Date today = new Date();
        OffsetDateTime startOdt = age.toInstant().atOffset(ZoneOffset.UTC);
        OffsetDateTime endOdt = today.toInstant().atOffset(ZoneOffset.UTC);
        int years = Period.between(startOdt.toLocalDate(), endOdt.toLocalDate()).getYears();

        return years;
    }
}
