package com.kurocho.geogames.utils.sign_up;

import com.kurocho.geogames.api.sign_up.SignUpCredentials;
import com.kurocho.geogames.utils.exception.EmptyCredentialsException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class SignUpCredentialsVerifier {

    private SignUpCredentials credentials;

    @Inject
    SignUpCredentialsVerifier(){}

    void verify(SignUpCredentials credentials) throws EmptyCredentialsException {
        this.credentials = credentials;
        verifyCredentialsNotEmpty();
    }

    private void verifyCredentialsNotEmpty() throws EmptyCredentialsException {
        if(credentials.getUsername().isEmpty() || credentials.getEmail().isEmpty() || credentials.getPassword().isEmpty()){
            throw new EmptyCredentialsException();
        }
    }

}
