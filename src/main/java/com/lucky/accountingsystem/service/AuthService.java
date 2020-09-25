package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.exception.BadLoginException;

public class AuthService {

    private static boolean LOGGED_IN;
    private static String LOGGED_IN_USER;

    public static boolean isLoggedIn() {
        return LOGGED_IN;
    }

    public static void setLoggedIn(boolean loggedIn) {
        LOGGED_IN = loggedIn;
    }

    public static String getLoggedInUser() {
        return LOGGED_IN_USER;
    }

    public static void setLoggedInUser(String loggedInUser) {
        LOGGED_IN_USER = loggedInUser;
    }

    public static boolean authenticate(String email, String password) throws BadLoginException {
        // TODO: Get details from the database
//        if (false) { // TODO: Throw BadLoginException if the password is wrong
//            throw new BadLoginException("The login details were incorrect!");
//        }
        LOGGED_IN = true;
        LOGGED_IN_USER = email;

        return true;
    }

}
