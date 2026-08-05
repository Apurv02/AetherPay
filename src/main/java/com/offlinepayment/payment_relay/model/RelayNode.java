package com.offlinepayment.payment_relay.model;

import lombok.Data;

@Data
public class RelayNode {

    private String nodeName;
    private boolean hasInternet;
}