package com.projectcourse.projectcourse.controller;

import org.springframework.web.bind.annotation.RestController;

import com.projectcourse.projectcourse.entity.Course;
import com.projectcourse.projectcourse.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/course/getAll")
    public ResponseEntity<?> getMethodName() {
        return courseService.getAll();
    }

    @PostMapping("course/addCourse")
    public ResponseEntity<?> saveCourse(@RequestBody Course entity, @RequestHeader("Authorization") String token) {
        return courseService.addCourse(entity, token);
    }

    @GetMapping("course/getById/{id}")
    public ResponseEntity<?> getMethodName(@PathVariable Integer id) {
        return courseService.fetchACourse(id);
    }

    @PutMapping("course/updateCourse/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable Integer id, @RequestBody Course course) {
        return courseService.modifyCourse(course, id);
    }

    @DeleteMapping("course/delete/{id}")
    public ResponseEntity<?> courseDelete(@PathVariable Integer id) {
        return courseService.deleteCourse(id);
    }

}
