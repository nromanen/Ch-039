package com.hospitalsearch.util;


import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;


public class UserDetailRegisterDto {



    /**
     *SHOULD BE REMOVED -> UserDto (validation)
     */



    @NotEmpty
    @Size(min = 2, max = 25)
    @Pattern(regexp = "[A-Z][a-z]*", message = "Incorrect name")
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 25)
    @Pattern(regexp = "[A-Z][a-z]*", message = "Incorrect name")
    private String lastName;

    @NotEmpty
    @Digits(message = "wrong phone number", integer = 10, fraction = 0)
    private String phone;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
//    @NotNull
    private LocalDate birthDate;

    private String imagePath;

    @NotNull
    private Gender gender;

    @NotEmpty
    private String address;





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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
