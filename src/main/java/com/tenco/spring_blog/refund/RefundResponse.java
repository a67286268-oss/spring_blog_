package com.tenco.spring_blog.refund;

import com.tenco.spring_blog._core.util.MyDateUtil;
import lombok.Builder;
import lombok.Data;

public class RefundResponse {

     @Data
     public static class ListDto {
         private Long id;   // 환불 요청 PK
         private int amount;
         private String paymentId; //
         private String createdAt; // 결제 요청 시간

         @Builder
         public ListDto(Refund refund) {
             this.id = refund.getId();
             this.amount = refund.getPayment().getAmount();
             this.paymentId = refund.getPayment().getPaymentId(); // 주문 번호
             this.createdAt = MyDateUtil.timestampFormat(refund.getCreatedAt());
         }
     }
}
