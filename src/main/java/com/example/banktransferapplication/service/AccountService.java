package com.example.banktransferapplication.service;
import com.example.banktransferapplication.model.Account;
import javax.security.auth.login.AccountNotFoundException;

public interface AccountService {
    Account deposit(Long accountId, Double amount) throws AccountNotFoundException;

}
