package com.campus.activity.service;

import com.campus.activity.dto.ActivityRequest;
import com.campus.activity.entity.Activity;
import com.campus.activity.entity.Registration;
import com.campus.activity.repository.ActivityRepository;
import com.campus.activity.repository.RegistrationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final RegistrationRepository registrationRepository;

    public ActivityService(ActivityRepository activityRepository,
                           RegistrationRepository registrationRepository) {
        this.activityRepository = activityRepository;
        this.registrationRepository = registrationRepository;
    }

    public List<Activity> listAll() {
        return activityRepository.findAllByOrderByCreatedAtDesc();
    }

    public Activity getById(Long id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("活动不存在"));
    }

    public Activity create(ActivityRequest req, Long userId) {
        Activity activity = new Activity();
        activity.setTitle(req.getTitle());
        activity.setDescription(req.getDescription());
        activity.setLocation(req.getLocation());
        activity.setStartTime(req.getStartTime());
        activity.setEndTime(req.getEndTime());
        activity.setMaxCapacity(req.getMaxCapacity());
        activity.setCurrentNum(0);
        activity.setStatus(1);
        activity.setCreatedBy(userId);
        activity.setQrCodeKey(UUID.randomUUID().toString().replace("-", "").substring(0, 16));
        return activityRepository.save(activity);
    }

    public Activity update(Long id, ActivityRequest req) {
        Activity activity = getById(id);
        activity.setTitle(req.getTitle());
        activity.setDescription(req.getDescription());
        activity.setLocation(req.getLocation());
        activity.setStartTime(req.getStartTime());
        activity.setEndTime(req.getEndTime());
        activity.setMaxCapacity(req.getMaxCapacity());
        return activityRepository.save(activity);
    }

    public void updateStatus(Long id, Integer status) {
        Activity activity = getById(id);
        activity.setStatus(status);
        activityRepository.save(activity);
    }

    @Transactional
    public String register(Long activityId, Long userId) {
        Activity activity = activityRepository.findByIdWithLock(activityId)
                .orElseThrow(() -> new RuntimeException("活动不存在"));

        if (activity.getStatus() != 1) {
            throw new RuntimeException("当前活动不在报名阶段");
        }

        if (registrationRepository.existsByUserIdAndActivityId(userId, activityId)) {
            throw new RuntimeException("已报名，请勿重复报名");
        }

        if (activity.getCurrentNum() >= activity.getMaxCapacity()) {
            throw new RuntimeException("报名人数已满");
        }

        activity.setCurrentNum(activity.getCurrentNum() + 1);
        activityRepository.save(activity);

        Registration reg = new Registration();
        reg.setUserId(userId);
        reg.setActivityId(activityId);
        registrationRepository.save(reg);

        return "报名成功";
    }

    public List<Registration> getMyRegistrations(Long userId) {
        return registrationRepository.findByUserId(userId);
    }
}
