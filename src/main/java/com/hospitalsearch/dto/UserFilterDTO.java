package com.hospitalsearch.dto;

/**
 * @author Andrew Jasinskiy on 28.05.16
 */

public class UserFilterDTO {

    //searchFields
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String status;
    private String allField;

    //pagination fields
    private int totalPage;
    private Integer currentPage;
    private Integer pageSize;
    //default sort
    private String sort = "email";
    private Boolean asc = false;

    public UserFilterDTO(Integer pageSize, String sort, Boolean asc) {
        this.pageSize = pageSize;
        this.sort = sort;
        this.asc = asc;
    }

    public UserFilterDTO() {
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

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Boolean getAsc() {
        return asc;
    }

    public void setAsc(Boolean asc) {
        this.asc = asc;
    }

    @Override
    public String toString() {
        return "UserFilterDTO{" +
                "role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                ", allField='" + allField + '\'' +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", sort='" + sort + '\'' +
                ", asc=" + asc + '}';
    }
}
