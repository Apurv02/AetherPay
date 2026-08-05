package com.offlinepayment.payment_relay.controller;

import com.offlinepayment.payment_relay.model.Account;
import com.offlinepayment.payment_relay.model.PaymentRequest;
import com.offlinepayment.payment_relay.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // tells spring that this class receive http request
@RequiredArgsConstructor
@RequestMapping("/api") //set base address for all methods
public class PaymentController {    //Payment controller file receive the request from the browser or any app to the server

    private final AccountService accountService;

    @GetMapping("/hello")  //It allows ->hello method to rum whenever a get request came from an API
    public String hello() {
        return "welcome to offline payment relay system!";
    }
    
    @PostMapping("/accounts")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }
    @GetMapping("/accounts")
    public List<Account> getAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping("/payment/send")
    public String sendPayment(@RequestBody PaymentRequest request) {
        return accountService.sendPayment(request);
    }
}














