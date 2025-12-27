package com.projectcourse.projectcourse.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.projectcourse.projectcourse.response.FailedResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<FailedResponse> handleCustomException(CustomException ex) {
        return ResponseEntity.status(HttpStatusCode.valueOf(ex.getStatusCode()))
                .body(new FailedResponse(ex.getMessage()));
    }
}
