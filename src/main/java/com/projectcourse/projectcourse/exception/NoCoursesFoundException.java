package com.projectcourse.projectcourse.exception;

public class NoCoursesFoundException extends RuntimeException {
    public NoCoursesFoundException(){
        super("No Course Found");
    }

    public NoCoursesFoundException (String message){
        super(message);
    }
}
