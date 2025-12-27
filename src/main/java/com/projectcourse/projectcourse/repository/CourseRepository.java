package com.projectcourse.projectcourse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projectcourse.projectcourse.entity.Course;

public interface CourseRepository extends JpaRepository<Course , Integer> {
    public Optional<Course> findByCourseId(Integer id);
}
