package sg.edu.nus.iss.ibfb4ssfassessment.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import sg.edu.nus.iss.ibfb4ssfassessment.util.PastOnly;

public class Login {
    // @NotNull (message = "Mandatory field")
    @NotEmpty (message = "Mandatory field")
    @Email (message = "Must be a valid email format")
    @Size (max = 50, message = "Maximum length of 50 characters only")
    private String email;
    
    // @Past // cannot validate today date 
    @DateTimeFormat (pattern = "yyyy-MM-dd")
    @PastOnly (message = "Birthday cannot be a current or future date")
    @NotNull
    private Date birthDate;

    public Login() {
    }

    public Login(String email, Date birthDate) {
        this.email = email;
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Login [email=" + email + ", birthDate=" + birthDate + "]";
    }  
         
}
