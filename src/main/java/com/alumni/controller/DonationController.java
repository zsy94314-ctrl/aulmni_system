package com.alumni.controller;

import com.alumni.dto.DonationDTO;
import com.alumni.dto.DonationProjectDTO;
import com.alumni.entity.Donation;
import com.alumni.security.SecurityUser;
import com.alumni.service.CertificateService;
import com.alumni.service.DonationService;
import com.alumni.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donation")
public class DonationController {
    @Autowired
    private DonationService donationService;
    @Autowired
    private CertificateService certificateService;

    @GetMapping("/projects")
    public Result<?> listProjects(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize) {
        return donationService.listProjects(pageNum, pageSize);
    }

    @GetMapping("/projects/{id}")
    public Result<?> getProject(@PathVariable Long id) {
        return donationService.getProjectById(id);
    }

    @PostMapping("/projects")
    public Result<?> saveProject(@RequestBody DonationProjectDTO dto) {
        return donationService.saveProject(dto);
    }

    @PutMapping("/projects/{id}")
    public Result<?> updateProject(@PathVariable Long id, @RequestBody DonationProjectDTO dto) {
        dto.setId(id);
        return donationService.updateProject(dto);
    }

    @DeleteMapping("/projects/{id}")
    public Result<?> deleteProject(@PathVariable Long id) {
        return donationService.deleteProject(id);
    }

    @PostMapping
    public Result<?> donate(@RequestBody DonationDTO dto, Authentication auth) {
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        return donationService.donate(dto, user.getId());
    }

    @GetMapping("/my")
    public Result<?> myDonations(Authentication auth) {
        SecurityUser user = (SecurityUser) auth.getPrincipal();
        return donationService.getMyDonations(user.getId());
    }

    @GetMapping("/certificate/{donationId}")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long donationId) {
        Donation d = (Donation) donationService.getMyDonations(1L).getData();
        byte[] pdf = certificateService.generateCertificate("捐赠人", "捐赠项目", "100.00", "CERT20240101", "2024-01-01");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificate.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
