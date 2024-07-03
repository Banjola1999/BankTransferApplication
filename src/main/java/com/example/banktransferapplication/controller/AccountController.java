package com.example.banktransferapplication.controller;
import com.example.banktransferapplication.model.Account;
import com.example.banktransferapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.security.auth.login.AccountNotFoundException;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable Long accountId, @RequestParam Double amount) throws AccountNotFoundException {
        if (accountId == null || amount == null || amount <= 0) {
            return ResponseEntity.badRequest().build();
        }

        Account updatedAccount = accountService.deposit(accountId, amount);
        return ResponseEntity.ok(updatedAccount);
    }


}
