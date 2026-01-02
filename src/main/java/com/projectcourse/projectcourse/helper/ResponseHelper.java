package com.projectcourse.projectcourse.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.projectcourse.projectcourse.response.ApiResponse;
import com.projectcourse.projectcourse.response.FailedResponse;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ResponseHelper {
    public static ResponseEntity<ApiResponse<?>> createResponse(ApiResponse<?> response, HttpStatus status) {
        return ResponseEntity.status(status).body(response);
    }

    public static ResponseEntity<ApiResponse<?>> createResponse(String message, Object data, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse<>(message, data));
    }

    public static ResponseEntity<ApiResponse<?>> createSuccessResponse(String message, Object data) {
        return createResponse(message, data, HttpStatus.OK);
    }

    public static ResponseEntity<FailedResponse> createFailedResponse(FailedResponse response , HttpStatus status){
        return ResponseEntity.status(status).body(response);
    }
}
