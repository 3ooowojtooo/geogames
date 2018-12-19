package com.kurocho.geogames.utils.sign_up;

import com.kurocho.geogames.api.sign_up.SignUpCredentials;
import com.kurocho.geogames.utils.exception.EmptyCredentialsException;
import com.kurocho.geogames.utils.exception.InvalidEmailException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class SignUpCredentialsVerifier {

    private static final String EMAIL_REGEXP = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private SignUpCredentials credentials;

    @Inject
    SignUpCredentialsVerifier(){}

    void verify(SignUpCredentials credentials) throws EmptyCredentialsException, InvalidEmailException {
        this.credentials = credentials;
        verifyCredentialsNotEmpty();
        verifyEmail();
    }

    private void verifyCredentialsNotEmpty() throws EmptyCredentialsException {
        if(credentials.getUsername().isEmpty() || credentials.getEmail().isEmpty() || credentials.getPassword().isEmpty()){
            throw new EmptyCredentialsException();
        }
    }

    private void verifyEmail() throws InvalidEmailException {
        if(!credentials.getEmail().matches(EMAIL_REGEXP)){
            throw new InvalidEmailException();
        }
    }

}
