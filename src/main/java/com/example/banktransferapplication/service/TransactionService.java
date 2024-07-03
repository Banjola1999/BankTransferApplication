package com.example.banktransferapplication.service;
import com.example.banktransferapplication.dto.TransferRequestDto;
import com.example.banktransferapplication.model.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
Transaction processTransfer(TransferRequestDto request);
List<Transaction> getTransactions(String status, String accountNumber, String dateRange);
List<Transaction> getTransactionsForDate(LocalDate date);

    @Transactional
    String generateTransactionSummary(LocalDate date);
}

