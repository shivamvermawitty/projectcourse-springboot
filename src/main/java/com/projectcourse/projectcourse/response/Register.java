package com.projectcourse.projectcourse.response;

import com.projectcourse.projectcourse.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    private String message;
    private User user;
}
