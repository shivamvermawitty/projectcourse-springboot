package com.projectcourse.projectcourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.projectcourse.projectcourse.service.LectureService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class LectureController {
    @Autowired
    private LectureService lectureService;
    @GetMapping("lecture/getAll")
    public ResponseEntity<?> getMethodName() {
        return lectureService.getAll();
    }
    
}
