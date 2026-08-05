package com.offlinepayment.payment_relay.service;

import com.offlinepayment.payment_relay.model.Transaction;
import com.offlinepayment.payment_relay.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

}