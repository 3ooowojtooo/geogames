package com.kurocho.geogames.utils.sign_in;

import com.kurocho.geogames.api.SignInCredentials;
import com.kurocho.geogames.utils.exception.EmptyCredentialsException;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class SignInCredentialsVerifier {

    private SignInCredentials credentials;

    @Inject
    public SignInCredentialsVerifier(){
    }

    void verify(SignInCredentials credentials) throws EmptyCredentialsException {
        this.credentials = credentials;
        verifyCredentialsNotEmpty();
    }

    private void verifyCredentialsNotEmpty() throws EmptyCredentialsException{
        if(credentials.getUsername().isEmpty() || credentials.getPassword().isEmpty()){
            throw new EmptyCredentialsException();
        }
    }
}
