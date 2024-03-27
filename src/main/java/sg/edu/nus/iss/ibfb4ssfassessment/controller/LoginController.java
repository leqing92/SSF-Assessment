package sg.edu.nus.iss.ibfb4ssfassessment.controller;

import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession; 
import sg.edu.nus.iss.ibfb4ssfassessment.model.Login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class LoginController {

    // TODO: Task 6
    @GetMapping(path = {"/","index.html","/login"})
    public String login(Model model) {
        Login login = new Login();
        model.addAttribute("login", login);
        return "view0";
    }

    // TODO: Task 7
    @PostMapping(path = "/login")
    public String processlogin(HttpSession session, Model model, @Valid @ModelAttribute ("login") Login login, BindingResult binding) {
        if(binding.hasErrors()){
            model.addAttribute("login", login);
            return "view0";
        }
        else{
            session.setAttribute("login", login);
            Integer age = ageCal(login.getBirthDate());
            session.setAttribute("age", age);
            System.out.println(login.toString());
            System.out.println(age);
            return "view1";
        }
    }    

    // For the logout button shown on View 2
    // On logout, session should be cleared
    @GetMapping(path = "/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    
    private Integer ageCal(Date age){
        Date today = new Date();
        OffsetDateTime startOdt = age.toInstant().atOffset(ZoneOffset.UTC);
        OffsetDateTime endOdt = today.toInstant().atOffset(ZoneOffset.UTC);
        int years = Period.between(startOdt.toLocalDate(), endOdt.toLocalDate()).getYears();

        return years;
    }
}
