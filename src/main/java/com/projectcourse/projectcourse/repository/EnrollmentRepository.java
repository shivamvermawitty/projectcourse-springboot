package com.projectcourse.projectcourse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projectcourse.projectcourse.entity.Enrollment;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment , Integer> {
    
}
