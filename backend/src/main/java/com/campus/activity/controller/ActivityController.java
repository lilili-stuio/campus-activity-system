package com.campus.activity.controller;

import com.campus.activity.dto.ActivityRequest;
import com.campus.activity.dto.ApiResult;
import com.campus.activity.entity.Activity;
import com.campus.activity.entity.Registration;
import com.campus.activity.service.ActivityService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public ApiResult<List<Activity>> list() {
        return ApiResult.success(activityService.listAll());
    }

    @GetMapping("/{id}")
    public ApiResult<Activity> detail(@PathVariable Long id) {
        return ApiResult.success(activityService.getById(id));
    }

    @PostMapping
    public ApiResult<Activity> create(@Valid @RequestBody ActivityRequest req,
                                       @RequestAttribute("userId") Long userId) {
        return ApiResult.success(activityService.create(req, userId));
    }

    @PutMapping("/{id}")
    public ApiResult<Activity> update(@PathVariable Long id,
                                       @Valid @RequestBody ActivityRequest req) {
        return ApiResult.success(activityService.update(id, req));
    }

    @PutMapping("/{id}/status")
    public ApiResult<String> updateStatus(@PathVariable Long id,
                                           @RequestParam Integer status) {
        activityService.updateStatus(id, status);
        return ApiResult.success("状态更新成功");
    }

    @PostMapping("/{id}/register")
    public ApiResult<String> register(@PathVariable Long id,
                                       @RequestAttribute("userId") Long userId) {
        return ApiResult.success(activityService.register(id, userId));
    }

    @GetMapping("/my/registrations")
    public ApiResult<List<Registration>> myRegistrations(@RequestAttribute("userId") Long userId) {
        return ApiResult.success(activityService.getMyRegistrations(userId));
    }
}
