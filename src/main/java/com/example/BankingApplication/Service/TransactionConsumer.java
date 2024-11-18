package com.example.BankingApplication.Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    @KafkaListener(topics = "transaction_created", groupId = "banking_group")
    public void listenTransactionCreated(String message) {
        System.out.println("Received transaction event: " + message);
        // Process transaction logic here
    }

    @KafkaListener(topics = "account_updated", groupId = "banking_group")
    public void listenAccountUpdated(Object account) {
        System.out.println("Received account updated event: " + account);
        // Process account update logic here
    }
}
