package com.alumni.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("duplicate_record")
public class DuplicateRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long alumniId1;
    private Long alumniId2;
    private String alumniName1;
    private String alumniName2;
    private Double similarity;
    private Integer status;
    private String action;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
