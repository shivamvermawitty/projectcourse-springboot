package com.projectcourse.projectcourse.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(){
        super("Database Connection failed");
    }
    public DatabaseException(String message){
        super(message);
    }
}
