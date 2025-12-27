package com.projectcourse.projectcourse.dto;

import com.projectcourse.projectcourse.entity.Course;

import lombok.Getter;

@Getter
public class CourseDTO {
    private Integer courseId;
    private String title;
    private Integer instructorId;
    private String description;
    private double price;

    public CourseDTO(Course course) {
        this.courseId = course.getCourseId();
        this.title = course.getTitle();
        this.instructorId = course.getInstructorId();
        this.description = course.getDescription();
        this.price = course.getPrice();
    }

}
