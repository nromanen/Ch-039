package com.hospitalsearch.dto;

import com.hospitalsearch.entity.Role;
import com.hospitalsearch.service.annotation.PasswordMatch;
import com.hospitalsearch.service.annotation.UniqueEmail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Andrew Jasinskiy on 17.06.16
 */
@PasswordMatch(first  = "password", second = "confirmPassword", message = "The password fields must match")
public class UserRegisterDTO {

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
    @Size(min = 6, max = 20, message = "Your password must between 6 and 20 characters")
    @Pattern(regexp = PASSWORD_PATTERN, message = "Your password is too weak")
    private String password;

    @NotEmpty(message = "Please enter your password again.")
    private String confirmPassword;

    private Set<Role> userRoles = new HashSet<>();

    private Boolean enabled = false;

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Role> userRoles) {
        this.userRoles = userRoles;
    }

    public UserRegisterDTO() {
    }

    public UserRegisterDTO(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserRegisterDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", userRoles=" + userRoles +
                ", enabled=" + enabled +
                '}';
    }
}
