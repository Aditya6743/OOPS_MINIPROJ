package com.bank.service;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;
import com.bank.model.Account;
import com.bank.model.CurrentAccount;
import com.bank.model.Customer;
import com.bank.model.SavingsAccount;
import com.bank.model.Transaction;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {
    private List<Account> accounts;
    private FileManager fileManager;
    
    public Bank() {
        this.accounts = new ArrayList<>();
        this.fileManager = new FileManager();
        
        File dataDir = new File("data");
        File accountsFile = new File("data/accounts.txt");
        if (dataDir.exists() && accountsFile.exists() && accountsFile.length() > 0) {
            fileManager.loadData(this);
        } else {
            // Add some dummy data for testing UI if files don't exist
            Customer c1 = new Customer("C001", "John Doe");
            Account a1 = new SavingsAccount("1001", c1, 5000.0, 4.0);
            
            Customer c2 = new Customer("C002", "Jane Smith");
            Account a2 = new CurrentAccount("1002", c2, 10000.0, 2000.0);
            
            this.accounts.add(a1);
            this.accounts.add(a2);
            saveData();
        }
    }
    
    private void saveData() {
        fileManager.saveAccounts(accounts);
        fileManager.saveTransactions(accounts);
    }
    
    public void addAccount(Account account) {
        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }
    
    public Account createSavingsAccount(String customerId, String name, double initialBalance) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        if (customerId == null || customerId.trim().isEmpty()) throw new IllegalArgumentException("Customer ID cannot be empty");
        Customer customer = new Customer(customerId, name);
        String accNum = "10" + UUID.randomUUID().toString().substring(0, 8);
        Account acc = new SavingsAccount(accNum, customer, initialBalance, 4.0);
        accounts.add(acc);
        saveData();
        return acc;
    }
    
    public Account createCurrentAccount(String customerId, String name, double initialBalance) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        if (customerId == null || customerId.trim().isEmpty()) throw new IllegalArgumentException("Customer ID cannot be empty");
        Customer customer = new Customer(customerId, name);
        String accNum = "20" + UUID.randomUUID().toString().substring(0, 8);
        Account acc = new CurrentAccount(accNum, customer, initialBalance, 2000.0);
        accounts.add(acc);
        saveData();
        return acc;
    }
    
    public Account findAccount(String accountNumber) throws AccountNotFoundException {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accountNumber)) {
                return acc;
            }
        }
        throw new AccountNotFoundException("Account number " + accountNumber + " not found.");
    }
    
    public void deposit(String accountNumber, double amount) throws AccountNotFoundException, InvalidAmountException {
        Account acc = findAccount(accountNumber);
        acc.deposit(amount);
        saveData();
    }
    
    public void withdraw(String accountNumber, double amount) throws AccountNotFoundException, InvalidAmountException, InsufficientBalanceException {
        Account acc = findAccount(accountNumber);
        acc.withdraw(amount);
        saveData();
    }
    
    
    public void transfer(String fromAccount, String toAccount, double amount) throws AccountNotFoundException, InvalidAmountException, InsufficientBalanceException {
        if (fromAccount.equals(toAccount)) {
            throw new IllegalArgumentException("Cannot transfer to the same account.");
        }
        Account src = findAccount(fromAccount);
        Account dest = findAccount(toAccount);
        
        if (amount <= 0) {
            throw new InvalidAmountException("Transfer amount must be positive.");
        }
        
        src.withdraw(amount);
        try {
            dest.deposit(amount);
        } catch (Exception e) {
            src.deposit(amount); // rollback
            src.removeLastTransaction(); // remove rollback tx
            src.removeLastTransaction(); // remove withdrawal tx
            throw e;
        }
        
        src.removeLastTransaction();
        dest.removeLastTransaction();
        
        src.addTransactionToHistory(new Transaction(UUID.randomUUID().toString(), LocalDateTime.now(), "Transfer Out", amount, "To " + toAccount, src.getAccountNumber(), src.getBalance()));
        dest.addTransactionToHistory(new Transaction(UUID.randomUUID().toString(), LocalDateTime.now(), "Transfer In", amount, "From " + fromAccount, dest.getAccountNumber(), dest.getBalance()));
        
        saveData();
    }

    
    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }
    
    public List<Transaction> getAllTransactions() {
        List<Transaction> all = new ArrayList<>();
        for (Account a : accounts) {
            all.addAll(a.getTransactions());
        }
        all.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate())); // Newest first
        return all;
    }
}
