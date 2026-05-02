package com.alumni.controller;

import com.alumni.service.AlipayService;
import com.alumni.service.DonationService;
import com.alumni.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pay")
public class PayController {
    @Autowired
    private AlipayService alipayService;
    @Autowired
    private DonationService donationService;

    @PostMapping("/create")
    public Result<?> createOrder(@RequestParam String orderNo, @RequestParam String amount, @RequestParam String subject) {
        try {
            String form = alipayService.createPayForm(orderNo, amount, subject);
            return Result.success(form);
        } catch (Exception e) {
            return Result.error("支付创建失败: " + e.getMessage());
        }
    }

    @PostMapping("/notify")
    public String notify(@RequestParam String out_trade_no) {
        donationService.confirmPayment(out_trade_no);
        return "success";
    }

    @PostMapping("/confirm")
    public Result<?> confirm(@RequestParam String orderNo) {
        return donationService.confirmPayment(orderNo);
    }
}
