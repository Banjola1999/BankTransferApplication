package com.example.banktransferapplication.controller;

import com.example.banktransferapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionSummaryController {

    private final TransactionService transactionService;

    @GetMapping("/summary")
    public ResponseEntity<String> getTransactionSummary(@RequestParam LocalDate date) {
        String summary = transactionService.generateTransactionSummary(date);
        return ResponseEntity.ok(summary);
    }
}