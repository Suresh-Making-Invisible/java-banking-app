package com.example.BankingApplication.Service;

import com.example.BankingApplication.Model.Account;
import com.example.BankingApplication.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    // Create a new account
    public Account createAccount(String name) {
        Account account = new Account();
        account.setName(name);
        accountRepository.save(account);

        kafkaTemplate.send("account_updated", account);
        return account;
    }

    // Deposit money into an account
    public Account deposit(String accountNumber, BigDecimal amount) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isEmpty()) {
            throw new IllegalArgumentException("Account not found");
        }

        Account account = optionalAccount.get();
        account.setBalance(account.getBalance().add(amount));
        kafkaTemplate.send("transaction_created", "Deposit of " + amount + " to account " + accountNumber);
        return accountRepository.save(account);
    }

    // Credit (withdraw) money from an account
    public Account credit(String accountNumber, BigDecimal amount) {
        Optional<Account> optionalAccount = accountRepository.findByAccountNumber(accountNumber);
        if (optionalAccount.isEmpty()) {
            throw new IllegalArgumentException("Account not found");
        }

        Account account = optionalAccount.get();
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(amount));

        kafkaTemplate.send("transaction_created", "Withdrawal of " + amount + " from account " + accountNumber);

        return accountRepository.save(account);
    }

    // Get account details
    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
    }
}
