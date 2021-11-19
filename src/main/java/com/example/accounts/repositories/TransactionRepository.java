package com.example.accounts.repositories;

import com.example.accounts.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
    List<Transaction> findByUsernameOrigin(String usernameOrigin);
    List<Transaction> findByUsernameDestiny(String usernameDestiny);
}
