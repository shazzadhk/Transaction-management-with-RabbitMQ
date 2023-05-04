package com.shazzad.paymentservice.controller;

import com.shazzad.paymentservice.model.Account;
import com.shazzad.paymentservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> saveAccount(@RequestBody Account account){
        return new ResponseEntity<>(accountService.createAccount(account), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts(){
        return new ResponseEntity<List<Account>>(accountService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/getAccountById/{id}")
    public ResponseEntity<Account> getSingleAccount(@PathVariable("id") Long id){
        return new ResponseEntity<>(accountService.getAccount(id),HttpStatus.OK);
    }
}
