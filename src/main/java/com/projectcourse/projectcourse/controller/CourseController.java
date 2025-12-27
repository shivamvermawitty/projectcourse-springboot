package com.projectcourse.projectcourse.controller;

import org.springframework.web.bind.annotation.RestController;

import com.projectcourse.projectcourse.dto.CourseDTO;
import com.projectcourse.projectcourse.entity.Course;
import com.projectcourse.projectcourse.response.ApiResponse;
import com.projectcourse.projectcourse.service.CourseService;
import static com.projectcourse.projectcourse.helper.ResponseHelper.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RequestMapping(value = "/course", produces = "application/json")
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public ResponseEntity<?> getMethodName() {
        return createSuccessResponse("Courses retrieved successfully", courseService.findAllCourses());
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> saveCourse(@RequestBody Course entity, @RequestHeader("Authorization") String token) {
        return courseService.addCourse(entity, token);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public ResponseEntity<?> getMethodName(@PathVariable Long id) {
        return courseService.fetchACourse(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return courseService.modifyCourse(course, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> courseDelete(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }

}
