package com.alumni.dto;

import lombok.Data;

@Data
public class ActivityQueryDTO {
    private String title;
    private String type;
    private Integer status;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
