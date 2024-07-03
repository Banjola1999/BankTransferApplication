package com.example.banktransferapplication.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
private Long fromAccountId;
private Long toAccountId;
private double amount;
private LocalDate date;
@Enumerated(EnumType.STRING)
private Status status;
private Double transactionFee;
private Double commission;
}