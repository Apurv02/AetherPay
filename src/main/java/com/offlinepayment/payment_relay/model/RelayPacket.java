package com.offlinepayment.payment_relay.model;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity
@Data
public class RelayPacket {

    @Id
    private String packetId;
    private String encryptedData;
    private int hopCount;
    private String currentNode;
    private String status;
    private int ttl;
    private int retryCount;
}