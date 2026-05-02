package com.alumni.mapper;

import com.alumni.entity.ActivityRegistration;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.util.List;

@Mapper
public interface ActivityRegistrationMapper extends BaseMapper<ActivityRegistration> {
    @Select("SELECT ar.*, a.name as alumni_name FROM activity_registration ar " +
            "LEFT JOIN alumni a ON ar.alumni_id = a.id WHERE ar.activity_id = #{activityId}")
    List<ActivityRegistration> selectByActivityId(Long activityId);

    @Update("UPDATE activity_registration SET checked_in = 1, check_in_time = NOW() WHERE id = #{id}")
    int checkIn(Long id);
}
