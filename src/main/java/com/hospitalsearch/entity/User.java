package com.hospitalsearch.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@PrimaryKeyJoinColumn(name = "id")

@org.hibernate.annotations.NamedQueries({@org.hibernate.annotations.NamedQuery(name = User.SELECT_BY_ROLE, query = User.SELECT),})

public class User extends UserDetail implements Serializable {

    @NotNull
    @Column(unique = true, nullable = false)
    private String email;
    @NotNull
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean enabled = true;

    static final String SELECT = "SELECT u FROM User u JOIN  u.userRoles r WHERE r.id = :id";
    public static final String SELECT_BY_ROLE = "SELECT_BY_ROLE";

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ROLE_USERS", joinColumns = @JoinColumn(name = "USERS_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    @Fetch(FetchMode.SELECT)
    private Set<Role> userRoles = new HashSet<Role>();

    public User() {

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

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", userRoles=" + userRoles +
                '}';
    }
}
