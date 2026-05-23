package com.campus.activity.controller;

import com.campus.activity.dto.ApiResult;
import com.campus.activity.service.CheckinService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkin")
public class CheckinController {

    private final CheckinService checkinService;

    public CheckinController(CheckinService checkinService) {
        this.checkinService = checkinService;
    }

    @PostMapping
    public ApiResult<String> checkin(@RequestParam Long activityId,
                                      @RequestParam String qrCodeKey,
                                      @RequestAttribute("userId") Long userId) {
        return ApiResult.success(checkinService.checkin(activityId, userId, qrCodeKey));
    }
}
