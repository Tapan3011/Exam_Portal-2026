package com.examportal.interfaces;

public interface Authenticable {

    // Every class that implements this must define login
    boolean login(String email, String password);

    // Every class that implements this must define logout
    void logout();
}