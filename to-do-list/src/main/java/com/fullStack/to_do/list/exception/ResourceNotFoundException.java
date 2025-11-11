package com.fullStack.to_do.list.exception;

public class ResourceNotFoundException extends RuntimeException{

    public ResourceNotFoundException(){}
    public ResourceNotFoundException(String msg){super(msg);}
}
