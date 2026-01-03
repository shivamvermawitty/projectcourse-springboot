package com.projectcourse.projectcourse.service;

import static com.projectcourse.projectcourse.helper.ResponseHelper.createFailedResponse;
import static com.projectcourse.projectcourse.helper.ResponseHelper.createResponse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.projectcourse.projectcourse.dto.CourseDTO;
import com.projectcourse.projectcourse.entity.Course;
import com.projectcourse.projectcourse.entity.Lecture;
import com.projectcourse.projectcourse.entity.User;
import com.projectcourse.projectcourse.exception.CustomException;
import com.projectcourse.projectcourse.exception.ForbiddenException;
import com.projectcourse.projectcourse.exception.UnauthorizedException;
import com.projectcourse.projectcourse.helper.ResponseHelper;
import com.projectcourse.projectcourse.helper.Util;
import com.projectcourse.projectcourse.repository.CourseRepository;
import com.projectcourse.projectcourse.repository.LectureRepository;
import com.projectcourse.projectcourse.repository.UserRepository;
import com.projectcourse.projectcourse.response.ApiResponse;
import com.projectcourse.projectcourse.response.FailedResponse;
import com.projectcourse.projectcourse.response.Success;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private LectureRepository lectureRepository;

    public User getUserByRequest(HttpServletRequest request) {
        String token=Util.getToken(request);
        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email).orElseThrow(() ->new CustomException("User Not Found" ,404 ));
        return user;

    }

    public Course getCourseByCourseId(Long id){
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new CustomException("No Course Found", 404));
        return course;
    }

    public ResponseEntity<?> findAllCourses() {
        try {
            List<Course> courses = courseRepository.findAll();

            if (courses.isEmpty()) {
                throw new CustomException("Course Not Found", 404);
            }
            List<CourseDTO> courseList=courses.stream().map(CourseDTO::new).toList();
            return ResponseHelper.createResponse(new ApiResponse<>("Data fetched successfully", courseList), HttpStatus.OK) ;

        } catch (UnauthorizedException e) {
            throw new CustomException("You are not authorized", 401);

        } catch (ForbiddenException e) {
            throw new CustomException("Access is forbidden", 402);
        }
    }

    public ResponseEntity<?> addCourse(Course course, HttpServletRequest request) {
        User user=getUserByRequest(request);
        course.setInstructorId(user.getId());
        try {
            return ResponseEntity.ok(courseRepository.save(course));
        } catch (DataIntegrityViolationException ex) {
            throw new CustomException("User is Already Enrolled", 409);
        } catch (TransactionSystemException ex) {
            throw new CustomException("Invalid Transaction", 409);
        } catch (DataAccessResourceFailureException ex) {
            throw new CustomException("Database Connection failed", 509);
        }
    }

    public ResponseEntity<?> fetchACourse(Long id) {
        try {
            return ResponseEntity.ok(courseRepository.findById(id));
        } catch (Exception e) {
            throw new CustomException("No Course Found", 404);
        }
    }

    public ResponseEntity<?> modifyCourse(Course course, Long id , HttpServletRequest request) {
        User user= getUserByRequest(request);
        if(course.getInstructorId()!=user.getId()) return ResponseHelper.createFailedResponse(new FailedResponse("You are not authorized to update this course"), HttpStatus.FORBIDDEN);
        Course existingCourse = getCourseByCourseId(id);
        if (course.getDescription() != null)
            existingCourse.setDescription(course.getDescription());
        if (course.getInstructorId() != null)
            existingCourse.setInstructorId(course.getInstructorId());
        existingCourse.setLectures(course.getLectures());
        if (course.getPrice() != 0.0)
            existingCourse.setPrice(course.getPrice());
        if (course.getTitle() != null)
            existingCourse.setTitle(course.getTitle());

        if (course.getLectures() != null) {
            List<Lecture> updatedLectures = new ArrayList<>();

            for (Lecture lecture : course.getLectures()) {
                if (lecture.getId() != null) {
                    Lecture managedLecture = lectureRepository.findById(lecture.getId())
                            .orElseThrow(
                                    () -> new CustomException("Lecture Not Found", 404));
                    managedLecture.setDescription(lecture.getDescription());
                    managedLecture.setThumbnail(lecture.getThumbnail());
                    managedLecture.setTitle(lecture.getTitle());
                    managedLecture.setVideoUrl(lecture.getVideoUrl());
                    updatedLectures.add(managedLecture);
                }
            }
            existingCourse.setLectures(updatedLectures);
        }

        return ResponseEntity.ok(courseRepository.save(existingCourse));
    }

    public ResponseEntity<?> deleteCourse(Long id) {
        try {
            courseRepository.deleteById(id);
            return ResponseEntity.ok(new Success("Course deleted successfully."));
        } catch (EmptyResultDataAccessException ex) {
            throw new CustomException("Course with id " + id + " not found.", 404);
        }
    }

    public ResponseEntity<?> addLectureToCourse(Lecture lecture, Long courseId , HttpServletRequest request) {
        User user= getUserByRequest(request);
        Course course=getCourseByCourseId(courseId);
        if(user.getId()!=course.getInstructorId()) return createFailedResponse(new FailedResponse("User unauthorized to add course") , HttpStatus.FORBIDDEN);
        lecture.setCourse(course);
        course.getLectures().add(lecture);
        return createResponse(new ApiResponse<Course>("Lecture Successfully", courseRepository.save(course)), HttpStatus.OK);    
    }

}
