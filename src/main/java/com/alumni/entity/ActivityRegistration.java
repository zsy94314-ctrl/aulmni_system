package com.alumni.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("activity_registration")
public class ActivityRegistration {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long activityId;
    private Long alumniId;
    @TableField(exist = false)
    private String alumniName;
    private Integer status;
    private String remark;
    private Integer checkedIn;
    private LocalDateTime checkInTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
