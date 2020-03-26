package com.finaltest.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "auth_logs")
public class AuthLog {

    public static final String Succsess = "Succsess";
    public static final String Filed = "Failed";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "AuthDate", updatable = false, nullable = false)
    private String date;

    @Column
    private String email;

    @Column
    private String status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_auth_id" ,nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private AuthModel userAuth;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AuthModel getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(AuthModel userAuth) {
        this.userAuth = userAuth;
    }
}
