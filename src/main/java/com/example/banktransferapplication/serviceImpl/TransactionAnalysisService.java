package com.example.banktransferapplication.serviceImpl;
import com.example.banktransferapplication.model.Status;
import com.example.banktransferapplication.model.Transaction;
import com.example.banktransferapplication.repository.TransactionRepository;
import com.example.banktransferapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class TransactionAnalysisService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionAnalysisService.class);
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    @Transactional
    public void processAnalysis() {
        List<Transaction> transactions = transactionRepository.findByStatus(Status.SUCCESSFUL);
        for (Transaction transaction : transactions) {
            double transactionFee = Math.min(transaction.getAmount() * 0.005, 100);
            double commission = transactionFee * 0.2;
            transaction.setTransactionFee(transactionFee);
            transaction.setCommission(commission);
            transactionRepository.save(transaction);
            logger.info("Processed transaction id: {}, amount: {}, fee: {}, commission: {}",
                    transaction.getId(), transaction.getAmount(), transactionFee, commission);
        }
    }



    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduledTransactionSummary() {
        LocalDate today = LocalDate.now();
        String summary = transactionService.generateTransactionSummary(today);
        logger.info(summary);
    }


}

