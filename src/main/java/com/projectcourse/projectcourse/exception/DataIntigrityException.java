package com.projectcourse.projectcourse.exception;

public class DataIntigrityException extends RuntimeException {

    public DataIntigrityException(){
        super("Invalid Course Data");
    }

    public DataIntigrityException(String message){
        super(message);
    }
    
}
