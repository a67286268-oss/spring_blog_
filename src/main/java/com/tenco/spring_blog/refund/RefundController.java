package com.tenco.spring_blog.refund;

import com.tenco.spring_blog._core.util.Define;
import com.tenco.spring_blog.payment.PaymentResponse;
import com.tenco.spring_blog.user.SessionUserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.engine.Mode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RefundController {

    private final RefundService refundService;

    // paymentId (주문서 )
    // payment.id  (pk)
    // GET http://localhost:8080/refund/request/{paymentPk}
    @GetMapping("/refund/request/{paymentPk}")
    public String requestForm(@PathVariable Long paymentPk, HttpSession session, Model model) {
        SessionUserDto sessionUserDto = (SessionUserDto) session.getAttribute(Define.SESSION_USER);
        PaymentResponse.ListDto paymentListDto = refundService.findRequestForm(paymentPk, sessionUserDto.getId());
        model.addAttribute("payment", paymentListDto);
        return "refund/request-form";
    }
}
