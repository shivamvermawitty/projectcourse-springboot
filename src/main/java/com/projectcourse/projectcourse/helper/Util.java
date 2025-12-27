package com.projectcourse.projectcourse.helper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.projectcourse.projectcourse.entity.User;
import com.projectcourse.projectcourse.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

public class Util {

    @Autowired
    private static UserRepository userRepository;

    public static String getToken(HttpServletRequest request){
        String header=request.getHeader("Authorization");
        String token=header.substring(7);
        return token;
    }

    public static Optional<User> getUserByUsername(String username){
        return userRepository.findByEmail(username);
    } 
}
