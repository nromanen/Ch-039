package com.hospitalsearch.dto;

import com.hospitalsearch.service.annotation.FieldMatch;
import com.hospitalsearch.service.annotation.UniqueEmail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 * @author Andrew Jasinskiy on 17.06.16
 */
@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match")
public class UserRegisterDto {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String PASSWORD_PATTERN =
            "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

    @NotEmpty(message = "Please enter your email")
    @Email
    @UniqueEmail(message = "User with this email is already exists")
    @Pattern(regexp = EMAIL_PATTERN, message = "Please enter email in correct format.")
    private String email;

    @NotEmpty(message = "Please enter your password.")
    @Size(min = 6, max = 16, message = "Your password must between 6 and 16 characters")
    @Pattern(regexp = PASSWORD_PATTERN,
            message = "Your password must contains at least:one lowercase characters, one uppercase characters")
    private String password;

    @NotEmpty(message = "Please enter your password again.")
    private String confirmPassword;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public UserRegisterDto() {
    }
}
