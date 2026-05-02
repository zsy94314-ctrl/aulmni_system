package com.alumni.service.impl;

import com.alumni.dto.ActivityQueryDTO;
import com.alumni.entity.Activity;
import com.alumni.entity.ActivityRegistration;
import com.alumni.mapper.ActivityMapper;
import com.alumni.mapper.ActivityRegistrationMapper;
import com.alumni.service.ActivityService;
import com.alumni.vo.PageResult;
import com.alumni.vo.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityRegistrationMapper registrationMapper;

    @Override
    public Result<PageResult<Activity>> list(ActivityQueryDTO query) {
        Page<Activity> page = new Page<>(query.getPageNum(), query.getPageSize());
        LambdaQueryWrapper<Activity> qw = new LambdaQueryWrapper<>();
        if (query.getTitle() != null) qw.like(Activity::getTitle, query.getTitle());
        if (query.getType() != null) qw.eq(Activity::getType, query.getType());
        if (query.getStatus() != null) qw.eq(Activity::getStatus, query.getStatus());
        qw.eq(Activity::getDeleted, 0).orderByDesc(Activity::getCreateTime);
        Page<Activity> result = activityMapper.selectPage(page, qw);
        return Result.success(new PageResult<>(result.getTotal(), result.getRecords(), result.getCurrent(), result.getSize()));
    }

    @Override
    public Result<Activity> getById(Long id) {
        return Result.success(activityMapper.selectById(id));
    }

    @Override
    public Result<?> save(Activity activity) {
        activityMapper.insert(activity);
        return Result.success();
    }

    @Override
    public Result<?> update(Activity activity) {
        activityMapper.updateById(activity);
        return Result.success();
    }

    @Override
    public Result<?> delete(Long id) {
        activityMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Result<?> register(Long activityId, Long alumniId) {
        Activity activity = activityMapper.selectById(activityId);
        if (activity == null) return Result.error("活动不存在");
        if (activity.getMaxParticipants() != null && activity.getMaxParticipants() > 0
                && activity.getRegisteredCount() != null
                && activity.getRegisteredCount() >= activity.getMaxParticipants()) {
            return Result.error("报名人数已满");
        }
        LambdaQueryWrapper<ActivityRegistration> qw = new LambdaQueryWrapper<>();
        qw.eq(ActivityRegistration::getActivityId, activityId).eq(ActivityRegistration::getAlumniId, alumniId);
        if (registrationMapper.selectCount(qw) > 0) {
            return Result.error("您已报名该活动");
        }
        ActivityRegistration reg = new ActivityRegistration();
        reg.setActivityId(activityId);
        reg.setAlumniId(alumniId);
        reg.setStatus(1);
        registrationMapper.insert(reg);
        activityMapper.updateRegisteredCount(activityId, 1);
        return Result.success();
    }

    @Override
    public Result<?> cancelRegistration(Long registrationId) {
        ActivityRegistration reg = registrationMapper.selectById(registrationId);
        if (reg != null) {
            reg.setStatus(0);
            registrationMapper.updateById(reg);
            activityMapper.updateRegisteredCount(reg.getActivityId(), -1);
        }
        return Result.success();
    }

    @Override
    public Result<?> checkIn(Long registrationId) {
        registrationMapper.checkIn(registrationId);
        return Result.success();
    }
}
