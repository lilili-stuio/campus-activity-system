package com.campus.activity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String studentId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(nullable = false, length = 10)
    private String role;

    public User(String studentId, String name, String password, String role) {
        this.studentId = studentId;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
