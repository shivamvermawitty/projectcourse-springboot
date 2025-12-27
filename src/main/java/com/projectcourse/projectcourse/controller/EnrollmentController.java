package com.projectcourse.projectcourse.controller;

import org.springframework.web.bind.annotation.RestController;

import com.projectcourse.projectcourse.entity.Enrollment;
import com.projectcourse.projectcourse.service.EnrollmentService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/enrollment")
@RestController
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @GetMapping
    public ResponseEntity<?> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id);
    }

    @PostMapping
    public ResponseEntity<?> createEnrollment(@RequestBody Enrollment enrollment) {
        return enrollmentService.updateEnrollment(enrollment.getId(), enrollment);
    }

    @PostMapping("/enroll/{id}")
    public ResponseEntity<?> enrollUser(@PathVariable Long id, HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        return enrollmentService.enroll(id, token);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEnrollment(@PathVariable Long id, @RequestBody Enrollment enrollment) {
        return enrollmentService.updateEnrollment(id, enrollment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable Long id) {
        return enrollmentService.deleteEnrollment(id);
    }

}
