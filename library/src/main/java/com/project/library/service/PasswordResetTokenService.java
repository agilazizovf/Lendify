package com.project.library.service;

import javax.naming.AuthenticationException;

public interface PasswordResetTokenService {

    void initiatePasswordReset(String email) throws AuthenticationException;
    void resetPassword(String token, String newPassword);
}
