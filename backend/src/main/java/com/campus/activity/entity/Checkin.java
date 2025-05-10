package com.campus.activity.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "t_checkin")
public class Checkin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long activityId;

    @Column(nullable = false)
    private LocalDateTime checkinTime;

    @PrePersist
    protected void onCreate() {
        if (checkinTime == null) {
            checkinTime = LocalDateTime.now();
        }
    }
}
