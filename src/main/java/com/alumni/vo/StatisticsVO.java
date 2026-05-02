package com.alumni.vo;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class StatisticsVO {
    private Long totalAlumni;
    private Long totalActivities;
    private Long totalDonations;
    private BigDecimal totalDonationAmount;
    private List<Map<String, Object>> alumniByRegion;
    private List<Map<String, Object>> alumniByIndustry;
    private List<Map<String, Object>> donationTrend;
    private List<Map<String, Object>> activityParticipation;
    private List<Map<String, Object>> topDonors;
}
