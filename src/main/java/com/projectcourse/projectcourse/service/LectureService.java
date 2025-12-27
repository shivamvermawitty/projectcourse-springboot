package com.projectcourse.projectcourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projectcourse.projectcourse.repository.LectureRepository;

@Service
public class LectureService {
    @Autowired
    private LectureRepository lectureRepository;

    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(lectureRepository.findAll());
    }
}
