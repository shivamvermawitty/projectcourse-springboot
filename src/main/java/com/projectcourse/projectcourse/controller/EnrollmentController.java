package com.projectcourse.projectcourse.controller;

import org.springframework.web.bind.annotation.RestController;

import com.projectcourse.projectcourse.service.EnrollmentService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("enroll/{id}")
    public ResponseEntity<?> postMethodName(@PathVariable Integer id, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        System.out.println(token);
        return enrollmentService.enroll(id, token);
    }

}
