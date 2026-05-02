package com.alumni.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class DonationDTO {
    private Long projectId;
    private BigDecimal amount;
    private String payMethod;
    private String remark;
}
