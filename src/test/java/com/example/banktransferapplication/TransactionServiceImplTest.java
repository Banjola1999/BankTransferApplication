package com.example.banktransferapplication;

import com.example.banktransferapplication.dto.TransferRequestDto;
import com.example.banktransferapplication.model.Account;
import com.example.banktransferapplication.model.Status;
import com.example.banktransferapplication.model.Transaction;
import com.example.banktransferapplication.repository.AccountRepository;
import com.example.banktransferapplication.repository.TransactionRepository;
import com.example.banktransferapplication.serviceImpl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Account fromAccount;
    private Account toAccount;
    private TransferRequestDto request;

    @BeforeEach
    public void setUp() {
        fromAccount = new Account();
        fromAccount.setId(1L);
        fromAccount.setAccountNumber("123456");
        fromAccount.setBalance(1000.0);

        toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setAccountNumber("654321");
        toAccount.setBalance(1500.0);

        request = new TransferRequestDto();
        request.setFromAccountId(1L);
        request.setToAccountId(2L);
        request.setAmount(100.0);
    }

    @Test
    public void testProcessTransfer_Success() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        Transaction result = transactionService.processTransfer(request);

        assertEquals(Status.SUCCESSFUL, result.getStatus());
        assertEquals(100.0, result.getAmount());
        assertEquals(0.5, result.getTransactionFee());
        assertEquals(0.1, result.getCommission());
        Mockito.verify(transactionRepository).save(result);
    }

    @Test
    public void testProcessTransfer_InsufficientFunds() {
        fromAccount.setBalance(50.0);
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        Transaction result = transactionService.processTransfer(request);

        assertEquals(Status.INSUFFICIENT_FUNDS, result.getStatus());
        Mockito.verify(transactionRepository).save(result);
    }

    @Test
    public void testProcessTransfer_AccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        when(accountRepository.findById(2L)).thenReturn(Optional.empty());

        Transaction result = transactionService.processTransfer(request);

        assertEquals(Status.FAILED, result.getStatus());
        Mockito.verify(transactionRepository).save(result);
    }

    @Test
    public void testGetTransactionsByStatus() {
        List<Transaction> transactions = List.of(new Transaction());
        when(transactionRepository.findByStatus(Status.SUCCESSFUL)).thenReturn(transactions);

        List<Transaction> result = transactionService.getTransactions("SUCCESSFUL", null, null);

        assertEquals(transactions, result);
    }

    @Test
    public void testGetTransactionsByAccountNumber() {
        List<Transaction> transactions = List.of(new Transaction());
        when(transactionRepository.findByFromAccountIdOrToAccountId(1L, 1L)).thenReturn(transactions);

        List<Transaction> result = transactionService.getTransactions(null, "1", null);

        assertEquals(transactions, result);
    }

    @Test
    public void testGetTransactionsByDateRange() {
        List<Transaction> transactions = List.of(new Transaction());
        when(transactionRepository.findByDateBetween(any(LocalDate.class), any(LocalDate.class))).thenReturn(transactions);

        List<Transaction> result = transactionService.getTransactions(null, null, "2023-01-01:2023-01-31");

        assertEquals(transactions, result);
    }

    @Test
    public void testGenerateTransactionSummary() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(100.0);
        transaction1.setCommission(5.0);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(200.0);
        transaction2.setCommission(10.0);

        List<Transaction> transactions = List.of(transaction1, transaction2);
        when(transactionRepository.findAllByDate(any(LocalDate.class))).thenReturn(transactions);

        String summary = transactionService.generateTransactionSummary(LocalDate.now());

        assertEquals("Transaction Summary for " + LocalDate.now() + ": Total Amount: 300.0, Total Commission: 15.0", summary);
    }
}
