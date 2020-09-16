package com.lucky.accountingsystem.service;

import com.lucky.accountingsystem.exception.InvalidPasswordException;

@SuppressWarnings("Since15")
public class PasswordService {

    public boolean isValid(String password, String confirmPassword) throws InvalidPasswordException {
        if (password.isEmpty()) {
            throw new InvalidPasswordException("Password must not be empty.");
        }
        if (password.length() < 8) {
            throw new InvalidPasswordException("Password must contain at least 8 symbols.");
        }
        if (!password.equals(confirmPassword)) {
            throw  new InvalidPasswordException("Passwords do not match. Please reenter your passwords.");
        }
        return true;
    }

}
