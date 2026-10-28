package com.tenco.spring_blog.payment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public enum PaymentStatus {

    READY("결제 대기"),
    PAID("결제 완료");

    private final String displayName;
}
