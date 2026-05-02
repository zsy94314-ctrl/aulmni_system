package com.alumni.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("class")
public class Clazz {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private Long collegeId;
    @TableField(exist = false)
    private String collegeName;
    private Integer grade;
    private String major;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableLogic
    private Integer deleted;
}
