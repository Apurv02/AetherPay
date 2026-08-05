package com.offlinepayment.payment_relay.repository;

import com.offlinepayment.payment_relay.model.RelayPacket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelayPacketRepository
        extends JpaRepository<RelayPacket, String> {
}