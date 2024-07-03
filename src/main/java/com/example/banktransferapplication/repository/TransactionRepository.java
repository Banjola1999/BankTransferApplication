package com.example.banktransferapplication.repository;
import com.example.banktransferapplication.model.Status;
import com.example.banktransferapplication.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
List<Transaction> findByStatus(Status status);
List<Transaction> findByFromAccountIdOrToAccountId(Long fromAccountId, Long toAccountId);
List<Transaction> findByDateBetween(LocalDate startDate, LocalDate endDate);
List<Transaction> findByDate(LocalDate date);

List<Transaction> findAllByDate(LocalDate date);
}
