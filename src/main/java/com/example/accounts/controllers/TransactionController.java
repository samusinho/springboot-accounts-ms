package com.example.accounts.controllers;

import com.example.accounts.models.Account;
import com.example.accounts.models.Transaction;
import com.example.accounts.repositories.AccountRepository;
import com.example.accounts.repositories.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class TransactionController {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionController(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/transactions")
    Transaction newTransaction(@RequestBody Transaction transaction) {
        Account accountOrigin = accountRepository.findById(transaction.getUsernameOrigin()).orElse(null);
        Account accountDestiny = accountRepository.findById(transaction.getUsernameDestiny()).orElse(null);
        if (accountOrigin == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontró una cuenta con el usuario " + transaction.getUsernameOrigin());
        }
        if (accountDestiny == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se encontró una cuenta con el usuario " + transaction.getUsernameDestiny());
        }
        if (accountOrigin.getBalance() < transaction.getValue()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo insuficiente");
        }
        accountOrigin.setBalance(accountOrigin.getBalance() - transaction.getValue());
        accountOrigin.setLastChange(new Date());
        accountRepository.save(accountOrigin);

        accountDestiny.setBalance(accountDestiny.getBalance() + transaction.getValue());
        accountDestiny.setLastChange(new Date());
        accountRepository.save(accountDestiny);

        transaction.setDate(new Date());
        return transactionRepository.save(transaction);
    }

    @GetMapping("/transactions/{username}")
    List<Transaction> userTransaction(@PathVariable String username) {
        Account userAccount = accountRepository.findById(username).orElse(null);
        if (userAccount == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay cuenta con el usuario " + username);
        }
        List<Transaction> tOrigin = transactionRepository.findByUsernameOrigin(username);
        List<Transaction> tDestiny = transactionRepository.findByUsernameDestiny(username);
        List<Transaction> transactions = Stream.concat(tOrigin.stream(), tDestiny.stream()).collect(Collectors.toList());
        return  transactions;
    }
}
