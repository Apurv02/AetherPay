package com.offlinepayment.payment_relay.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class RelayHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String packetId;

    private String nodeName;

    private int hopNumber;

    private String status;

    private LocalDateTime timestamp;
}