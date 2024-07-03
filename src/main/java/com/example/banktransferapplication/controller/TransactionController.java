package com.example.banktransferapplication.controller;
import com.example.banktransferapplication.dto.TransferRequestDto;
import com.example.banktransferapplication.model.Transaction;
import com.example.banktransferapplication.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

private final TransactionService transactionService;

@Autowired
public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
}

@PostMapping("/transfer")
public Transaction transfer(@RequestBody TransferRequestDto request) {
    return transactionService.processTransfer(request);
}

@GetMapping("/all-transactions")
public List<Transaction> getTransactions(
        @RequestParam(required = false) String status,
        @RequestParam(required = false) String accountNumber,
        @RequestParam(required = false) String dateRange) {
    return transactionService.getTransactions(status, accountNumber, dateRange);
}
}

