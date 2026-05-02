package com.alumni.vo;

import lombok.Data;
import java.util.List;

@Data
public class GraphNodeVO {
    private Long id;
    private String name;
    private String category;
    private Integer value;
    private List<GraphEdgeVO> links;
}
