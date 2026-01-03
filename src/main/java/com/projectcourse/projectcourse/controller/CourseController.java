package com.projectcourse.projectcourse.controller;

import org.springframework.web.bind.annotation.RestController;

import com.projectcourse.projectcourse.entity.Course;
import com.projectcourse.projectcourse.entity.Lecture;
import com.projectcourse.projectcourse.service.CourseService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RequestMapping(value = "/course", produces = "application/json")
@RestController
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER' , 'STUDENT')")
    public ResponseEntity<?> getMethodName() {
        return courseService.findAllCourses();
    }

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> saveCourse(@RequestBody Course entity, HttpServletRequest request) {
        return courseService.addCourse(entity, request);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER' , 'STUDENT')")
    public ResponseEntity<?> getMethodName(@PathVariable Long id) {
        return courseService.fetchACourse(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> updateCourse(@PathVariable Long id, @RequestBody Course course , HttpServletRequest request) {
        return courseService.modifyCourse(course, id , request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('TEACHER','ADMIN')")
    public ResponseEntity<?> courseDelete(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }

    @PostMapping("/add-lecture/{id}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> postMethodName(@RequestBody Lecture lecture , @PathVariable Long id , HttpServletRequest request  ) {
        return courseService.addLectureToCourse(lecture , id ,request );
    }
    

}
