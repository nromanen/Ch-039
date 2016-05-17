package com.hospitalsearch.util;

import com.hospitalsearch.service.annotation.UniqueEmail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Short representation of user registration form
 */
public class UserRegisterDto {

    @NotEmpty
    @Size(min = 2, max = 25)
    @Pattern(regexp = "[A-Z][a-z]*", message = "Incorrect name")
    private String userName;

    @NotEmpty
    @Email
    @UniqueEmail(message = "User with this email is already exists")
    private String email;

    @NotEmpty(message = "Please enter your password.")
    @Size(min = 6, max = 15, message = "Your password must between 6 and 15 characters")
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
