package com.offlinepayment.payment_relay.model;

import lombok.Data;

@Data
public class PaymentRequest {

    private String paymentId;
    private Long senderId;
    private Long receiverId;
    private Double amount;

}