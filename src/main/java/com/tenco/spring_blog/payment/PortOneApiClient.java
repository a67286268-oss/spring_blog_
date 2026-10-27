package com.tenco.spring_blog.payment;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.net.PortUnreachableException;


// 포트원 서버와 HTTP 통신만 담당하는 클래스 (포트원 설정값도 이곳에서만 관리)
@Getter
@Component
public class PortOneApiClient {

    @Value("${portone.store-id}")
    private String storeId;
    @Value("${portone.channel-key}")
    private String channelKey;
    @Value("${portone.api-secret}")
    private String apiSecret;


    private static final String PAYMENT_URL = "https://api.portone.io/payments/";

    private final RestTemplate restTemplate = new RestTemplate();

    public void getPayment(String paymentId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "PortOne " + apiSecret);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        restTemplate.exchange(
                PAYMENT_URL + "/" + paymentId,
                HttpMethod.GET,
                request,
                String.class
        );

    }


}
