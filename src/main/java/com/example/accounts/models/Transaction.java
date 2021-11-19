package com.example.accounts.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Transaction {
    @Id
    private String id;
    private String usernameOrigin;
    private String usernameDestiny;
    private Integer value;
    private Date date;
}
