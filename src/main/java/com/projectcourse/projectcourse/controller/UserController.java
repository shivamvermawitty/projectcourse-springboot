package com.projectcourse.projectcourse.controller;

import org.springframework.web.bind.annotation.RestController;

import com.projectcourse.projectcourse.entity.User;
import com.projectcourse.projectcourse.helper.Util;
import com.projectcourse.projectcourse.response.FailedResponse;
import com.projectcourse.projectcourse.response.Login;
import com.projectcourse.projectcourse.response.Register;
import com.projectcourse.projectcourse.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id , HttpServletRequest request) {
        return userService.getUserById(id , request);
    }

    @PostMapping
    public ResponseEntity<Register> createUser(@RequestBody User user) {
        Register register = userService.saveUser(user);

        HttpStatus status = "User Created Successfully".equals(register.getMessage()) ? HttpStatus.OK
                : HttpStatus.CONFLICT;

        return ResponseEntity.status(status).body(register);
    }


    @PostMapping("/add-user")
    public ResponseEntity<Register> addUser(@RequestBody User user) {
        Register register = userService.saveUser(user);

        HttpStatus status = "User Created Successfully".equals(register.getMessage()) ? HttpStatus.OK
                : HttpStatus.CONFLICT;

        return ResponseEntity.status(status).body(register);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user , HttpServletRequest request) {
        return userService.updateUser(user, id , request);
    }

    // Of no use
    // @PutMapping("/update/{id}")
    // public ResponseEntity<Register> updateUserLegacy(@PathVariable Long id, @RequestBody User user) {
    //     Register register = userService.updateUser(user, id);

    //     HttpStatus status = "User updated Successfully".equals(register.getMessage()) ? HttpStatus.OK
    //             : HttpStatus.CONFLICT;

    //     return ResponseEntity.status(status).body(register);
    // }

    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody User user) {
        Login login = userService.verify(user);

        HttpStatus status = "success".equals(login.getStatus()) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(login);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/enrolled-courses")
    @PreAuthorize("hasAnyRole('STUDENT')")
    public ResponseEntity<?> getEnrolledCourse(HttpServletRequest request) {
        return userService.getEnrolledCourses(request);
    }

    @GetMapping("/enrolled-users/{id}")
    public ResponseEntity<?> getEnrolledUser(@PathVariable Long id, HttpServletRequest request) {

        String token = Util.getToken(request);

        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new FailedResponse("Invalid token"));

        return ResponseEntity.ok(userService.getEnrolledUser(token, id));
    }

}
