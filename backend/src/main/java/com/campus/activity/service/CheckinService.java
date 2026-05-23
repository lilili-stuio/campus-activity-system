package com.campus.activity.service;

import com.campus.activity.entity.Activity;
import com.campus.activity.entity.Checkin;
import com.campus.activity.repository.ActivityRepository;
import com.campus.activity.repository.CheckinRepository;
import com.campus.activity.repository.RegistrationRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckinService {

    private final CheckinRepository checkinRepository;
    private final RegistrationRepository registrationRepository;
    private final ActivityRepository activityRepository;

    public CheckinService(CheckinRepository checkinRepository,
                          RegistrationRepository registrationRepository,
                          ActivityRepository activityRepository) {
        this.checkinRepository = checkinRepository;
        this.registrationRepository = registrationRepository;
        this.activityRepository = activityRepository;
    }

    public String checkin(Long activityId, Long userId, String qrCodeKey) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new RuntimeException("活动不存在"));

        if (!activity.getQrCodeKey().equals(qrCodeKey)) {
            throw new RuntimeException("签到码无效");
        }

        if (!registrationRepository.existsByUserIdAndActivityId(userId, activityId)) {
            throw new RuntimeException("未报名该活动，无法签到");
        }

        if (checkinRepository.existsByUserIdAndActivityId(userId, activityId)) {
            throw new RuntimeException("已签到，请勿重复签到");
        }

        Checkin checkin = new Checkin();
        checkin.setUserId(userId);
        checkin.setActivityId(activityId);
        checkinRepository.save(checkin);

        return "签到成功";
    }
}
