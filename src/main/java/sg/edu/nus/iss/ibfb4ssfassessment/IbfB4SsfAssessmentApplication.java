package sg.edu.nus.iss.ibfb4ssfassessment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.service.DatabaseService;
import sg.edu.nus.iss.ibfb4ssfassessment.service.FileService;
import sg.edu.nus.iss.ibfb4ssfassessment.util.Util;

// TODO: Put in the necessary code as described in Task 1 & Task 2
@SpringBootApplication
public class IbfB4SsfAssessmentApplication implements CommandLineRunner {

	@Autowired
	FileService fileSvc;

	@Autowired
	DatabaseService movieSvc;
	public static void main(String[] args) {
		SpringApplication.run(IbfB4SsfAssessmentApplication.class, args);
	}

	// TODO: Task 2
	@Override
	public void run(String... args) throws Exception {
		List<Movie> movies= fileSvc.readFile(Util.MOVIEFILE);
		for(Movie movie : movies){
			movieSvc.saveRecord(movie);
		}
	}

}
