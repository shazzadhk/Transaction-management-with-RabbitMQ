package com.shazzad.paymentservice.service;

import com.shazzad.paymentservice.model.Account;
import com.shazzad.paymentservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }

    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Account getAccount(Long id){

        Account account = null;

        Optional<Account> accountOptional = accountRepository.findById(id);
        if(accountOptional.isPresent())
            account = accountOptional.get();
        return account;
    }

    public Account getAccountByUser(Long userId){
        Account account = null;

        Optional<Account> accountOptional = accountRepository.findByUserId(userId);
        if(accountOptional.isPresent())
            account = accountOptional.get();
        return account;
    }
}
