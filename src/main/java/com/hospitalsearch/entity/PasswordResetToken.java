package com.hospitalsearch.entity;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;


/**
 * @author Andrew Jasinskiy on 20.06.16
 */

@NamedQueries({
        @NamedQuery(name = "GET_RESET_PASSWORD_TOKEN_BY_NAME", query = "SELECT p FROM PasswordResetToken p WHERE p.token = :token"),
        @NamedQuery(name = "GET_RESET_PASSWORD_TOKEN_BY_USER", query = "SELECT p FROM PasswordResetToken p WHERE p.user = :user"),
})

@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetToken {
    //token valid 24 hours
    public static Integer RESET_PASSWORD_TOKEN_EXPIRATION = 24;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_reset_token_gen")
    @SequenceGenerator(name = "password_reset_token_gen", sequenceName = "password_reset_token_gen_id_seq", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expirydate")
    private Date expiryDate;

    public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expiryDate = calculateExpiryDate(RESET_PASSWORD_TOKEN_EXPIRATION * 60);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "PasswordResetToken{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", user=" + user +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
