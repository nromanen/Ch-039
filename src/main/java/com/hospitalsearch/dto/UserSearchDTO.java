package com.hospitalsearch.dto;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * @author Andrew Jasinskiy on 28.05.16
 */
public class UserSearchDTO {

    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String status;
    private String allField;

    public UserSearchDTO() {
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAllField() {
        return allField;
    }

    public void setAllField(String allField) {
        this.allField = allField;
    }

    @Override
    public String toString() {
        return "UserSearchDTO{" +
                "role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                ", allField='" + allField + '\'' +
                '}';
    }
}
