package com.alumni.controller;

import com.alumni.dto.ActivityQueryDTO;
import com.alumni.entity.Activity;
import com.alumni.security.SecurityUser;
import com.alumni.service.ActivityService;
import com.alumni.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @GetMapping
    public Result<?> list(ActivityQueryDTO query) {
        return activityService.list(query);
    }

    @GetMapping("/{id}")
    public Result<?> getById(@PathVariable Long id) {
        return activityService.getById(id);
    }

    @PostMapping
    public Result<?> save(@RequestBody Activity activity) {
        return activityService.save(activity);
    }

    @PutMapping("/{id}")
    public Result<?> update(@PathVariable Long id, @RequestBody Activity activity) {
        activity.setId(id);
        return activityService.update(activity);
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        return activityService.delete(id);
    }

    @PostMapping("/{id}/register")
    public Result<?> register(@PathVariable Long id, Authentication auth) {
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        return activityService.register(id, user.getId());
    }

    @PostMapping("/registrations/{regId}/cancel")
    public Result<?> cancel(@PathVariable Long regId) {
        return activityService.cancelRegistration(regId);
    }

    @PostMapping("/registrations/{regId}/checkin")
    public Result<?> checkIn(@PathVariable Long regId) {
        return activityService.checkIn(regId);
    }
}
