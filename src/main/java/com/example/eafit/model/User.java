package com.example.eafit.model;

import java.util.UUID;

public class User {
    private String id;
    private String userName;
    private String password;
    private String email;
    private boolean isActive;

    public User(String userName, String password, String email) {
        this.id = UUID.randomUUID().toString();
        this.userName = userName;
        this.password = password;
        this.email = email;
        isActive = true;
    }

    public void setId(String id) {this.id = id;}

    public String getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
