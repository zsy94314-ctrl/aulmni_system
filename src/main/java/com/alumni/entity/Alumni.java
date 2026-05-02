package com.alumni.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("alumni")
public class Alumni {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String studentNo;
    private String name;
    private Long classId;
    @TableField(exist = false)
    private String className;
    @TableField(exist = false)
    private String collegeName;
    private String phone;
    private String email;
    private String industry;
    private String region;
    private String company;
    private String position;
    private String avatar;
    private Integer privacyLevel;
    private String educationBg;
    private String workExperience;
    private Long userId;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;
}
