package com.examportal.services;

import com.examportal.dao.UserDAO;
import com.examportal.interfaces.Authenticable;
import com.examportal.models.User;

public class AuthService implements Authenticable {

    private UserDAO userDAO = new UserDAO();
    private User loggedInUser = null;

    // Login method from Authenticable interface
    @Override
    public boolean login(String email, String password) {

        User user = userDAO.getUserByEmailAndPassword(email, password);

        if (user != null) {
            loggedInUser = user;
            System.out.println("\nLogin successful! Welcome " + user.getName());
            return true;
        } else {
            System.out.println("\nInvalid email or password. Try again.");
            return false;
        }
    }

    // Logout method from Authenticable interface
    @Override
    public void logout() {
        System.out.println("\nGoodbye " + loggedInUser.getName() + "!");
        loggedInUser = null;
    }

    // Get currently logged in user
    public User getLoggedInUser() {
        return loggedInUser;
    }
}