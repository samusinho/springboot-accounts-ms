package com.example.accounts.repositories;

import com.example.accounts.models.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account, String> {
    @Query("{'username': {'$regex': ?0}}")
    List<Account> findByRegexUsername(String regexp);
}
