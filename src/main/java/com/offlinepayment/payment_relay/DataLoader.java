package com.offlinepayment.payment_relay;

import com.offlinepayment.payment_relay.model.Account;
import com.offlinepayment.payment_relay.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final AccountRepository accountRepository;

    @Override
    public void run(String... args) throws Exception {
        Account apurv = new Account();
        apurv.setName("Apurv");
        apurv.setPhoneNumber("9999999999");
        apurv.setBalance(5000.0);

        Account rahul = new Account();
        rahul.setName("Rahul");
        rahul.setPhoneNumber("8888888888");
        rahul.setBalance(3000.0);

        accountRepository.save(apurv);
        accountRepository.save(rahul);

        System.out.println("Accounts created successfully!");
    }
}