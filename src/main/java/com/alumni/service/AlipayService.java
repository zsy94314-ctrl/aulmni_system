package com.alumni.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AlipayService {
    @Value("${alipay.app-id}")
    private String appId;
    @Value("${alipay.private-key}")
    private String privateKey;
    @Value("${alipay.public-key}")
    private String publicKey;
    @Value("${alipay.gateway-url}")
    private String gatewayUrl;
    @Value("${alipay.notify-url}")
    private String notifyUrl;
    @Value("${alipay.return-url}")
    private String returnUrl;

    public String createPayForm(String orderNo, String amount, String subject) throws Exception {
        AlipayClient client = new DefaultAlipayClient(gatewayUrl, appId, privateKey, "json", "UTF-8", publicKey, "RSA2");
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);
        
        // Build JSON safely using ObjectMapper to prevent JSON injection attacks
        Map<String, String> bizContent = new HashMap<>();
        bizContent.put("out_trade_no", orderNo);
        bizContent.put("total_amount", amount);
        bizContent.put("subject", subject);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        
        ObjectMapper mapper = new ObjectMapper();
        String jsonContent = mapper.writeValueAsString(bizContent);
        request.setBizContent(jsonContent);
        
        return client.pageExecute(request).getBody();
    }
}
