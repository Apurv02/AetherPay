package com.offlinepayment.payment_relay.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "processed_payments")
public class ProcessedPayment {

    @Id
    private String paymentId;

    private Long senderId;
    private Long receiverId;
    private Double amount;
}