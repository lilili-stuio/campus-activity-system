package com.campus.activity.repository;

import com.campus.activity.entity.Checkin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CheckinRepository extends JpaRepository<Checkin, Long> {

    Optional<Checkin> findByUserIdAndActivityId(Long userId, Long activityId);

    List<Checkin> findByActivityId(Long activityId);

    boolean existsByUserIdAndActivityId(Long userId, Long activityId);
}
