package com.projectcourse.projectcourse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.projectcourse.projectcourse.entity.Lecture;
import com.projectcourse.projectcourse.service.LectureService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/lecture")
@RestController
public class LectureController {
    @Autowired
    private LectureService lectureService;

    @GetMapping
    public ResponseEntity<?> getAllLectures() {
        return lectureService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLectureById(@PathVariable Long id) {
        return lectureService.getLectureById(id);
    }

    @PostMapping
    public ResponseEntity<?> createLecture(@RequestBody Lecture lecture) {
        return lectureService.createLecture(lecture);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLecture(@PathVariable Long id, @RequestBody Lecture lecture) {
        return lectureService.updateLecture(id, lecture);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLecture(@PathVariable Long id) {
        return lectureService.deleteLecture(id);
    }
}
