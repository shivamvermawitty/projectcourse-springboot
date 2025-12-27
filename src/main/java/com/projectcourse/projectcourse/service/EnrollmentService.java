package com.projectcourse.projectcourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.projectcourse.projectcourse.entity.Course;
import com.projectcourse.projectcourse.entity.Enrollment;
import com.projectcourse.projectcourse.entity.User;
import com.projectcourse.projectcourse.exception.CustomException;
import com.projectcourse.projectcourse.repository.CourseRepository;
import com.projectcourse.projectcourse.repository.EnrollmentRepository;
import com.projectcourse.projectcourse.repository.UserRepository;

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

    public ResponseEntity<?> enroll(Integer id, String token) {
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

}
