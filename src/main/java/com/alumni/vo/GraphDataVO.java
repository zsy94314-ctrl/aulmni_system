package com.alumni.vo;

import lombok.Data;
import java.util.List;

@Data
public class GraphDataVO {
    private List<GraphNodeVO> nodes;
    private List<GraphEdgeVO> edges;
}
