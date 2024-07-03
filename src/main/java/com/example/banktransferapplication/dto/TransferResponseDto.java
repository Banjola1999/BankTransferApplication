package com.example.banktransferapplication.dto;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class TransferResponseDto {
private String fromAccount;
private String toAccount;
private Double amount;
private LocalDateTime timestamp;
private String status;
}
