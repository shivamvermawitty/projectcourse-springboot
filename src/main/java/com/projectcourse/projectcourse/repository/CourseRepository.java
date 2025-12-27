package com.projectcourse.projectcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectcourse.projectcourse.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
