package com.example.banktransferapplication.dto;
import lombok.Data;
@Data
public class TransferRequestDto {
private Long fromAccountId;
private Long toAccountId;
private Double amount;
}
