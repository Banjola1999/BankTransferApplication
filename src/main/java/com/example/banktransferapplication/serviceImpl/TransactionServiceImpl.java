package com.example.banktransferapplication.serviceImpl;
import com.example.banktransferapplication.dto.TransferRequestDto;
import com.example.banktransferapplication.model.Account;
import com.example.banktransferapplication.model.Status;
import com.example.banktransferapplication.model.Transaction;
import com.example.banktransferapplication.repository.AccountRepository;
import com.example.banktransferapplication.repository.TransactionRepository;
import com.example.banktransferapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

@Override
@Transactional
public Transaction processTransfer(TransferRequestDto request) {
    Optional<Account> fromAccountOpt = accountRepository.findById(request.getFromAccountId());
    Optional<Account> toAccountOpt = accountRepository.findById(request.getToAccountId());

    Transaction transaction = new Transaction();
    transaction.setFromAccountId(request.getFromAccountId());
    transaction.setToAccountId(request.getToAccountId());
    transaction.setAmount(request.getAmount());
    transaction.setDate(LocalDate.now());

    if (fromAccountOpt.isEmpty() || toAccountOpt.isEmpty()) {
        transaction.setStatus(Status.FAILED);
        transactionRepository.save(transaction);
        return transaction;
    }

    Account fromAccount = fromAccountOpt.get();
    Account toAccount = toAccountOpt.get();

    if (fromAccount.getBalance() < request.getAmount()) {
        transaction.setStatus(Status.INSUFFICIENT_FUNDS);
        transactionRepository.save(transaction);
        return transaction;
    }

    double transactionFee = Math.min(request.getAmount() * 0.005, 100.0);
    double commission = transactionFee * 0.2;


    fromAccount.setBalance(fromAccount.getBalance() - request.getAmount() - transactionFee);
    toAccount.setBalance(toAccount.getBalance() + request.getAmount());

    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);

    transaction.setStatus(Status.SUCCESSFUL);
    transaction.setTransactionFee(transactionFee);
    transaction.setCommission(commission);

    transactionRepository.save(transaction);


    return transaction;
}


@Override
public List<Transaction> getTransactions(String status, String accountNumber, String dateRange) {

if (status != null) {
    return transactionRepository.findByStatus(Status.valueOf(status));
}
if (accountNumber != null) {
    Long accountId = Long.parseLong(accountNumber);
    return transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
}
if (dateRange != null) {
    String[] dates = dateRange.split(":");
    LocalDate startDate = LocalDate.parse(dates[0]);
    LocalDate endDate = LocalDate.parse(dates[1]);
    return transactionRepository.findByDateBetween(startDate, endDate);
}
return transactionRepository.findAll();
}

@Override
@Transactional
public List<Transaction> getTransactionsForDate(LocalDate date) {
return transactionRepository.findByDate(date);
}


@Override
@Transactional
public String generateTransactionSummary(LocalDate date) {
List<Transaction> transactions = transactionRepository.findAllByDate(date);
double totalAmount = transactions.stream().mapToDouble(Transaction::getAmount).sum();
double totalCommission = transactions.stream().mapToDouble(Transaction::getCommission).sum();
return "Transaction Summary for " + date + ": Total Amount: " + totalAmount + ", Total Commission: " + totalCommission;
}

}


