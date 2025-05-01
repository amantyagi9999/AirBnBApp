package com.projects.exception;

public class UnAuthorisedException extends RuntimeException{
    public UnAuthorisedException(String message) {
        super(message);
    }
}
