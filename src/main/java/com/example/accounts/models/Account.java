package com.example.accounts.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Account {
    @Id
    private String username;
    private Integer balance;
    private Date lastChange;
}
