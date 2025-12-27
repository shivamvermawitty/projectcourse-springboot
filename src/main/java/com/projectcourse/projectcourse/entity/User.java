package com.projectcourse.projectcourse.entity;

import static jakarta.persistence.EnumType.STRING;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.projectcourse.projectcourse.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
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
@SequenceGenerator(name = "entity_seq_gen", sequenceName = "user_sequence", allocationSize = 1)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends BaseEntity {
    private String name;
    private String email;
    private String password;

    @Enumerated(STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference("user-enrollments")
    private List<Enrollment> enrolled;
}
