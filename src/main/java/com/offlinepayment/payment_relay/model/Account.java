package com.offlinepayment.payment_relay.model;

import jakarta.persistence.*;
import lombok.Data;

@Data    //automatically set getter and setter to spring for file
@Entity  //it tells spring that this java class is actually a database table
@Table(name = "accounts")
public class Account {

    @Id  //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phoneNumber;

    private Double balance;
}
