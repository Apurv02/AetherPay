package com.offlinepayment.payment_relay.auth;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}