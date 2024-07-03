package com.example.banktransferapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BankTransferApplication {

public static void main(String[] args) {
    SpringApplication.run(BankTransferApplication.class, args);
}

}
