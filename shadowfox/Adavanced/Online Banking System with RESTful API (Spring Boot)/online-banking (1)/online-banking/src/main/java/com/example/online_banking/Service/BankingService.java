package com.example.online_banking.Service;

import com.example.online_banking.Entity.Account;
import com.example.online_banking.Entity.Transaction;
import com.example.online_banking.Repo.AccountRepository;
import com.example.online_banking.Repo.TransactionRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class BankingService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Create a new account
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // Authenticate user by email and password
    public boolean authenticate(String email, String password) {
        Optional<Account> account = accountRepository.findByEmailAndPassword(email, password);
        return account.isPresent();
    }

    // Transfer funds between accounts
    public String transferFunds(Long fromId, Long toId, double amount) {
        Optional<Account> fromAccountOpt = accountRepository.findById(fromId);
        Optional<Account> toAccountOpt = accountRepository.findById(toId);

        if (fromAccountOpt.isEmpty() || toAccountOpt.isEmpty()) {
            return "One or both accounts not found.";
        }

        Account fromAccount = fromAccountOpt.get();
        Account toAccount = toAccountOpt.get();

        if (fromAccount.getBalance() < amount) {
            return "Insufficient funds.";
        }

        // Update balances
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Record transaction
        Transaction transaction = new Transaction();
        transaction.setFromAccountId(fromId);
        transaction.setToAccountId(toId);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);

        return "Transfer successful.";
    }

    // Get transaction history for a specific account
    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByFromAccountIdOrToAccountId(accountId, accountId);
    }
}
