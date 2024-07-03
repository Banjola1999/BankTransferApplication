package com.example.banktransferapplication.serviceImpl;
import com.example.banktransferapplication.model.Account;
import com.example.banktransferapplication.repository.AccountRepository;
import com.example.banktransferapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

private final AccountRepository accountRepository;

@Override
@Transactional
public Account deposit(Long accountId, Double amount) throws AccountNotFoundException {
    if (accountId == null || amount == null) {
        throw new IllegalArgumentException("AccountId or amount cannot be null");
    }

    Optional<Account> accountOpt = accountRepository.findById(accountId);
    if (accountOpt.isPresent()) {
        Account account = accountOpt.get();
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    } else {
        throw new AccountNotFoundException("Account with id " + accountId + " not found");
    }
}

}