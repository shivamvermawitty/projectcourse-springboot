package com.projectcourse.projectcourse.exception;

public class TransactionException extends RuntimeException {
    
    public TransactionException(){
        super("Inavlid Transaction");
    }

    public TransactionException(String message){
        super(message);
    }
}
