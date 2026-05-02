package com.alumni.service;

import com.alumni.dto.DonationDTO;
import com.alumni.dto.DonationProjectDTO;
import com.alumni.entity.Donation;
import com.alumni.entity.DonationProject;
import com.alumni.vo.PageResult;
import com.alumni.vo.Result;

public interface DonationService {
    Result<PageResult<DonationProject>> listProjects(Integer pageNum, Integer pageSize);
    Result<DonationProject> getProjectById(Long id);
    Result<?> saveProject(DonationProjectDTO dto);
    Result<?> updateProject(DonationProjectDTO dto);
    Result<?> deleteProject(Long id);
    Result<?> donate(DonationDTO dto, Long alumniId);
    Result<?> confirmPayment(String orderNo);
    Result<?> getMyDonations(Long alumniId);
}
