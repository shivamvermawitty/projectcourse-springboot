package com.projectcourse.projectcourse.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projectcourse.projectcourse.entity.Course;
import com.projectcourse.projectcourse.entity.Enrollment;
import com.projectcourse.projectcourse.entity.User;
import com.projectcourse.projectcourse.exception.CustomException;
import static com.projectcourse.projectcourse.helper.ResponseHelper.createFailedResponse;
import com.projectcourse.projectcourse.helper.Util;
import com.projectcourse.projectcourse.repository.CourseRepository;
import com.projectcourse.projectcourse.repository.UserRepository;
import com.projectcourse.projectcourse.response.FailedResponse;
import com.projectcourse.projectcourse.response.Login;
import com.projectcourse.projectcourse.response.Register;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CourseRepository courseRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User getUserByToken(String token) {
        String email = jwtService.extractUsername(token);
        User user = userRepository.findByEmail(email).orElseThrow(() ->new CustomException("User Not Found" ,404 ));
        return user;

    }

    public Register saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return new Register("User with email " + user.getEmail() + " already exists", user);
        }
        user.setPassword(encoder.encode(user.getPassword()));

        return new Register("User Created Successfully", userRepository.save(user));
    }

    public Login verify(User loginCred) {
        User completeUser = userRepository.findByEmail(loginCred.getEmail())
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(completeUser.getEmail(), loginCred.getPassword()));

            // authentication successful
            return new Login(jwtService.generateToken(completeUser), "success");

        } catch (Exception ex) {
            // invalid username/password
            return new Login(null, "Invalid credentials");
        }

    }

    public Register updateUser(User user, Long userId) {
        User existingUser = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        ;

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            User userWithEmail = userRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("No user with given email"));
            if (!userId.equals(userWithEmail.getId())) {
                return new Register("User with email " + user.getEmail() + " already exists", user);
            }

        }

        existingUser.setEmail(user.getEmail());
        existingUser.setName(user.getName());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());

        return new Register("User updated Successfully", userRepository.save(existingUser));

    }

    public ResponseEntity<?> getAllUsers() {

        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new CustomException("No User Found" ,404 );
        }

        return ResponseEntity.ok(users);
    }

    public ResponseEntity<?> getUserById(Long id , HttpServletRequest request) {
        String token= Util.getToken(request);
        User loggedInUser =getUserByToken(token);
        if(loggedInUser.getId()!=id) return createFailedResponse(new FailedResponse("You are not authorized to access this user"),HttpStatus.FORBIDDEN);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException("User not found", 404));
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("User does not exist" ,404 ));
        userRepository.deleteById(id);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> getEnrolledCourses(String token) {
        try {
            User user = getUserByToken(token);
            List<Course> enrolledCourse = user.getEnrolled().stream().map(Enrollment::getCourse).toList();
            return ResponseEntity.ok(enrolledCourse);
        } catch (Exception e) {
            throw new CustomException(e.getMessage() ,404 );
        }
    }

    public ResponseEntity<?> getEnrolledUser(String token, Long id) {
        try {
            Course course = courseRepository.findById(id).orElseThrow(() -> new CustomException("Course not Found" ,404 ));
            List<User> enrolledUser = course.getEnrollments()
                    .stream()
                    .map(Enrollment::getUser)

                    .toList();
            return ResponseEntity.ok(enrolledUser);
        } catch (Exception e) {
            throw new CustomException(e.getMessage() ,404 );
        }
    }

}
