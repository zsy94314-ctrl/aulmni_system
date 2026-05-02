package com.alumni.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("donation")
public class Donation {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long projectId;
    @TableField(exist = false)
    private String projectTitle;
    private Long alumniId;
    @TableField(exist = false)
    private String alumniName;
    private BigDecimal amount;
    private String payMethod;
    private String certificateNo;
    private String orderNo;
    private Integer status;
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
