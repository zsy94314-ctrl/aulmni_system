package com.alumni.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class PayDTO {
    private Long projectId;
    private BigDecimal amount;
    private String projectName;
}
