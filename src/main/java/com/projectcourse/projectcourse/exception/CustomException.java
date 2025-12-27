package com.projectcourse.projectcourse.exception;


public class CustomException extends RuntimeException{

    private int statusCode;

    public CustomException(){
        super("Something Went Wrong");
        this.statusCode=500;
    }

    public CustomException(String message , int statusCode){
        super(message);
        this.statusCode=statusCode;
    }

    public int getStatusCode(){
        return this.statusCode;
    }
}
