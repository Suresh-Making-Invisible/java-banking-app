package com.example.BankingApplication.Controller;

import com.example.BankingApplication.Model.Account;
import com.example.BankingApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Create a new account
    @PostMapping("/create")
    public Account createAccount(@RequestParam String name) {
        return userService.createAccount(name);
    }

    // Deposit money
    @PostMapping("/{accountNumber}/deposit")
    public Account deposit(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        return userService.deposit(accountNumber, amount);
    }

    // Credit (withdraw) money
    @PostMapping("/{accountNumber}/credit")
    public Account credit(@PathVariable String accountNumber, @RequestParam BigDecimal amount) {
        return userService.credit(accountNumber, amount);
    }

    // Get account details
    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return userService.getAccount(accountNumber);
    }
}
