package com.projectcourse.projectcourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projectcourse.projectcourse.entity.Lecture;
import com.projectcourse.projectcourse.exception.CustomException;
import com.projectcourse.projectcourse.repository.LectureRepository;
import com.projectcourse.projectcourse.response.Success;

@Service
public class LectureService {
    @Autowired
    private LectureRepository lectureRepository;

    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(lectureRepository.findAll());
    }

    public ResponseEntity<?> getLectureById(Long id) {
        return ResponseEntity.ok(lectureRepository.findById(id)
                .orElseThrow(() -> new CustomException("Lecture not found", 404)));
    }

    public ResponseEntity<?> createLecture(Lecture lecture) {
        if (lecture.getTitle() == null || lecture.getTitle().isEmpty()) {
            throw new CustomException("Lecture title is required", 400);
        }
        return ResponseEntity.ok(lectureRepository.save(lecture));
    }

    public ResponseEntity<?> updateLecture(Long id, Lecture lectureDetails) {
        Lecture lecture = lectureRepository.findById(id)
                .orElseThrow(() -> new CustomException("Lecture not found", 404));
        if (lectureDetails.getTitle() != null) {
            lecture.setTitle(lectureDetails.getTitle());
        }
        if (lectureDetails.getVideoUrl() != null) {
            lecture.setVideoUrl(lectureDetails.getVideoUrl());
        }
        if (lectureDetails.getThumbnail() != null) {
            lecture.setThumbnail(lectureDetails.getThumbnail());
        }
        if (lectureDetails.getDescription() != null) {
            lecture.setDescription(lectureDetails.getDescription());
        }
        
        return ResponseEntity.ok(lectureRepository.save(lecture));
    }

    public ResponseEntity<?> deleteLecture(Long id) {
        try {
            lectureRepository.deleteById(id);
            return ResponseEntity.ok(new Success("Lecture deleted successfully"));
        } catch (EmptyResultDataAccessException ex) {
            throw new CustomException("Lecture with id " + id + " not found", 404);
        }
    }
}
