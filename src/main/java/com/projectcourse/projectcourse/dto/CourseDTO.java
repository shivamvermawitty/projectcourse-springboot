package com.projectcourse.projectcourse.dto;

import com.projectcourse.projectcourse.entity.Course;

import lombok.Getter;

@Getter
public class CourseDTO {
    private Long id;
    private String title;
    private Long instructorId;
    private String description;
    private double price;

    public CourseDTO(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.instructorId = course.getInstructorId();
        this.description = course.getDescription();
        this.price = course.getPrice();
    }

}
