package com.alumni.service;

import com.alumni.dto.ActivityQueryDTO;
import com.alumni.entity.Activity;
import com.alumni.entity.ActivityRegistration;
import com.alumni.vo.PageResult;
import com.alumni.vo.Result;

public interface ActivityService {
    Result<PageResult<Activity>> list(ActivityQueryDTO query);
    Result<Activity> getById(Long id);
    Result<?> save(Activity activity);
    Result<?> update(Activity activity);
    Result<?> delete(Long id);
    Result<?> register(Long activityId, Long alumniId);
    Result<?> cancelRegistration(Long registrationId);
    Result<?> checkIn(Long registrationId);
}
