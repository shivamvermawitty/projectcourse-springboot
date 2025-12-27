package com.projectcourse.projectcourse.exception;


public class ForbiddenException extends RuntimeException {
    
    public ForbiddenException(){
        super("Access is forbidden");
    }

    public ForbiddenException(String message){
        super(message);
    }

}
