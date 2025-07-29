package com.example.online_banking.Controller;

import com.example.online_banking.Entity.Account;
import com.example.online_banking.Entity.Transaction;
import com.example.online_banking.Service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class AccountController {

    @Autowired
    private BankingService bankingService;

    // Account Registration
    @PostMapping("/register")
    public Account register(@RequestBody Account account) {
        return bankingService.createAccount(account);
    }

    // Funds Transfer
    @PostMapping("/transfer")
    public String transfer(
            @RequestParam Long fromId,
            @RequestParam Long toId,
            @RequestParam double amount) {
        return bankingService.transferFunds(fromId, toId, amount);
    }

    // Get Transaction History
    @GetMapping("/transactions/{accountId}")
    public List<Transaction> getHistory(@PathVariable Long accountId) {
        return bankingService.getTransactions(accountId);
    }

    @PostMapping("/login")
    public String login(@RequestBody Account account) {
        String email = account.getEmail();
        String password = account.getPassword();

        boolean success = bankingService.authenticate(email, password);
        return success ? "Login successful" : "Invalid credentials";
    }

}
