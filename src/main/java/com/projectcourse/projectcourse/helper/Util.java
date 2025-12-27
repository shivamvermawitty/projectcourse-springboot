package com.projectcourse.projectcourse.helper;

import jakarta.servlet.http.HttpServletRequest;

public class Util {

    public static String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.substring(7);
        return token;
    }

}
