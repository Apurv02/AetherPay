package com.offlinepayment.payment_relay.service;

import com.offlinepayment.payment_relay.exception.AccountNotFoundException;
import com.offlinepayment.payment_relay.model.Account;
import com.offlinepayment.payment_relay.model.PaymentRequest;
import com.offlinepayment.payment_relay.model.ProcessedPayment;
import com.offlinepayment.payment_relay.model.Transaction;
import com.offlinepayment.payment_relay.repository.AccountRepository;
import com.offlinepayment.payment_relay.repository.ProcessedPaymentRepository;
import com.offlinepayment.payment_relay.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ProcessedPaymentRepository processedPaymentRepository;
    private final TransactionRepository transactionRepository;

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public String sendPayment(PaymentRequest request) {

        if (processedPaymentRepository.existsById(request.getPaymentId())) {
            return "Duplicate payment rejected!";
        }

        Account sender = accountRepository.findById(request.getSenderId())
                .orElseThrow(() -> new AccountNotFoundException("Sender account not found"));

        Account receiver = accountRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new AccountNotFoundException("Receiver account not found"));

        if (sender.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + request.getAmount());

        accountRepository.save(sender);
        accountRepository.save(receiver);

        ProcessedPayment processed = new ProcessedPayment();
        processed.setPaymentId(request.getPaymentId());
        processed.setSenderId(request.getSenderId());
        processed.setReceiverId(request.getReceiverId());
        processed.setAmount(request.getAmount());
        processedPaymentRepository.save(processed);
        Transaction transaction = new Transaction();

        transaction.setSenderId(request.getSenderId());
        transaction.setReceiverId(request.getReceiverId());
        transaction.setAmount(request.getAmount());
        transaction.setPaymentId(request.getPaymentId());
        transaction.setStatus("SUCCESS");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return "Payment successful!";
    }
}