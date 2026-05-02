package com.alumni.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DonationProjectDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal targetAmount;
    private LocalDate endDate;
    private String coverImage;
    private Integer status;
}
