package com.thoughtworks.springbootemployee.exception;

public class ResourceNotFoundException extends Exception{
    public ResourceNotFoundException() {
        super("Resource not found");
    }
}
