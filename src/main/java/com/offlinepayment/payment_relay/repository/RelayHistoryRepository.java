package com.offlinepayment.payment_relay.repository;

import com.offlinepayment.payment_relay.model.RelayHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RelayHistoryRepository
        extends JpaRepository<RelayHistory, Long> {
}