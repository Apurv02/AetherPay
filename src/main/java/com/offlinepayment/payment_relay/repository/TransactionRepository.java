package com.offlinepayment.payment_relay.repository;

import com.offlinepayment.payment_relay.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}