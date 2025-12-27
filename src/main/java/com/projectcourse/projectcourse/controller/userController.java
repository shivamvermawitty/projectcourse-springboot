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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class userController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/add-user")
    public ResponseEntity<Register> addUser(@RequestBody User user) {
        Register register = userService.saveUser(user);

        HttpStatus status = "User Created Successfully".equals(register.getMessage()) ? HttpStatus.OK
                : HttpStatus.CONFLICT;

        return ResponseEntity.status(status).body(register);
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<Register> updateUser(@PathVariable Integer id, @RequestBody User user) {
        Register register = userService.updateUser(user, id);

        HttpStatus status = "User updated Successfully".equals(register.getMessage()) ? HttpStatus.OK
                : HttpStatus.CONFLICT;

        return ResponseEntity.status(status).body(register);
    }

    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody User user) {
        Login login = userService.verify(user);

        HttpStatus status = "success".equals(login.getStatus()) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(login);
    }

    @GetMapping("/user/getAll")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("user/enrolledCourse")
    public ResponseEntity<?> getEnrolledCourse(HttpServletRequest request) {
        String token = Util.getToken(request);

        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new FailedResponse("Invalid token"));

        return ResponseEntity.ok(userService.getEnrolledCourses(token));
    }

    @GetMapping("user/enrolledUser/{id}")
    public ResponseEntity<?> getEnrolledUser(@PathVariable Integer id, HttpServletRequest request) {

        String token = Util.getToken(request);

        if (token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new FailedResponse("Invalid token"));

        return ResponseEntity.ok(userService.getEnrolledUser(token, id));
    }

}
