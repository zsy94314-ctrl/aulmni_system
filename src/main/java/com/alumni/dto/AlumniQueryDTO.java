package com.alumni.dto;

import lombok.Data;

@Data
public class AlumniQueryDTO {
    private String name;
    private String studentNo;
    private Long classId;
    private Long collegeId;
    private String industry;
    private String region;
    private Integer grade;
    private Integer privacyLevel;
    private Integer pageNum = 1;
    private Integer pageSize = 10;
}
