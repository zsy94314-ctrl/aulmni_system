package com.alumni.service;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
        request.setBizContent("{"out_trade_no":"" + orderNo + "","total_amount":"" + amount + "","subject":"" + subject + "","product_code":"FAST_INSTANT_TRADE_PAY"}");
        return client.pageExecute(request).getBody();
    }
}
