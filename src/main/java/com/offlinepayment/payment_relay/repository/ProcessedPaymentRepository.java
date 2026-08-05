package com.offlinepayment.payment_relay.repository;

import com.offlinepayment.payment_relay.model.ProcessedPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProcessedPaymentRepository extends JpaRepository<ProcessedPayment, String> {
}