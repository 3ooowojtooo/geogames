package com.kurocho.geogames.utils.exception;

public class EmptyCredentialsException extends Exception{

    public EmptyCredentialsException(){
        super();
    }

    public EmptyCredentialsException(String message){
        super(message);
    }
}
