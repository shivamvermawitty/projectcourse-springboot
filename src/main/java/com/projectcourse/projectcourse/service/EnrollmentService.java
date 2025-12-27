package com.projectcourse.projectcourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projectcourse.projectcourse.entity.Course;
import com.projectcourse.projectcourse.entity.Enrollment;
import com.projectcourse.projectcourse.entity.User;
import com.projectcourse.projectcourse.exception.CustomException;
import com.projectcourse.projectcourse.repository.CourseRepository;
import com.projectcourse.projectcourse.repository.EnrollmentRepository;
import com.projectcourse.projectcourse.repository.UserRepository;
import com.projectcourse.projectcourse.response.Success;

import io.jsonwebtoken.ExpiredJwtException;


@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<?> getAllEnrollments() {
        return ResponseEntity.ok(enrollmentRepository.findAll());
    }

    public ResponseEntity<?> getEnrollmentById(Long id) {
        return ResponseEntity.ok(enrollmentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Enrollment not found", 404)));
    }

    public ResponseEntity<?> enroll(Long id, String token) {
        try {
            String email = jwtService.extractUsername(token);
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new CustomException("User Not Found" ,404 ));
            Course course = courseRepository.findById(id).orElseThrow(() -> new CustomException("Course Not Found" ,404 ));
            Enrollment enrollment = new Enrollment();
            enrollment.setCourse(course);
            enrollment.setUser(user);
            return ResponseEntity.ok(enrollmentRepository.save(enrollment));
        } catch (ExpiredJwtException e) {
            throw new CustomException("Token expired" ,401 );

        }  catch (IllegalArgumentException e) {
            throw new CustomException("Token not provided" ,400);

        } catch (DataIntegrityViolationException e) {
            throw new CustomException("User is Already Enrolled" ,409 );

        } catch (Exception e) {
            throw new CustomException(e.getMessage() ,500 );

        }
    }

    public ResponseEntity<?> updateEnrollment(Long id, Enrollment enrollmentDetails) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new CustomException("Enrollment not found", 404));
        
        if (enrollmentDetails.getCourse() != null) {
            enrollment.setCourse(enrollmentDetails.getCourse());
        }
        if (enrollmentDetails.getUser() != null) {
            enrollment.setUser(enrollmentDetails.getUser());
        }
        
        return ResponseEntity.ok(enrollmentRepository.save(enrollment));
    }

    public ResponseEntity<?> deleteEnrollment(Long id) {
        try {
            enrollmentRepository.deleteById(id);
            return ResponseEntity.ok(new Success("Enrollment deleted successfully"));
        } catch (EmptyResultDataAccessException ex) {
            throw new CustomException("Enrollment with id " + id + " not found", 404);
        }
    }

}
