package com.projectcourse.projectcourse.entity;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SequenceGenerator(name = "entity_seq_gen", sequenceName = "course_sequence", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course extends BaseEntity {
    private String title;
    private Long instructorId;
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
