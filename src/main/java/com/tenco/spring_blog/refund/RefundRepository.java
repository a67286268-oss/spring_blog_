package com.tenco.spring_blog.refund;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    // 결제 (payment.id) 로 조회 기능 추가
    @Query("SELECT r FROM Refund r WHERE r.payment.id = :paymentPk")
    Optional<Refund> findByPaymentPk(@Param("paymentPk") Long paymentPk);

    @Query("SELECT r.payment.id FROM Refund r WHERE r.user.id = :userId")
    List<Long> findPaymentPkListByUserId(@Param("userId") Long userId);

    // 로그인한 사용자의 환불 요청 내역 (결제 정보 함께 조회 , 최신순)
    @Query("""
                SELECT r FROM Refund r 
                JOIN FETCH r.payment
                WHERE r.user.id = :userId
                ORDER BY r.id DESC
            """)
    List<Refund> findAllByUserIdJoinPayment(@Param("userId") Long userId);

    // 관리자 : 전체 환불 요청
    @Query("""
                SELECT r FROM Refund r 
                JOIN FETCH r.user 
                JOIN FETCH r.payment 
                ORDER BY r.id DESC
            """)
    List<Refund> findAllJoinUserAndPayment();

    @Query("""
                SELECT r FROM Refund r 
                JOIN FETCH r.user
                JOIN FETCH r.payment 
                WHERE r.id = :id
            """)
    Optional<Refund> findByIdJoinUserAndPayment(@Param("id") Long id);



}





