package com.projectcourse.projectcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectcourse.projectcourse.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture , Long> {
    
}
