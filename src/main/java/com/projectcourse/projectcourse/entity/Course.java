package com.projectcourse.projectcourse.entity;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer courseId;
    private String title;
    private Integer instructorId;
    @Column(length = 5000)
    private String description;

    private double price;
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL , fetch = FetchType.EAGER , orphanRemoval = true)
    @JsonManagedReference
    private List<Lecture> lectures;

    @OneToMany(mappedBy = "course" , cascade = CascadeType.ALL , fetch = FetchType.EAGER , orphanRemoval = true)
    @JsonManagedReference("course-enrollments")
    private List<Enrollment> enrollments;

}
