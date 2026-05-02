package com.alumni.vo;

import lombok.Data;

@Data
public class GraphEdgeVO {
    private Long source;
    private Long target;
    private String relation;
    private Double weight;
}
