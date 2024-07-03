package com.example.banktransferapplication.controller;
import com.example.banktransferapplication.serviceImpl.TransactionAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class TransactionAnalysisController {

    private final TransactionAnalysisService transactionAnalysisService;

    @PostMapping("/run")
    public ResponseEntity<String> runAnalysis() {
        transactionAnalysisService.processAnalysis();
        return ResponseEntity.ok("Analysis run successfully.");
    }


}
