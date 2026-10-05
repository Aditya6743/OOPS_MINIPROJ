package com.bank.model;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Account {
    private String accountNumber;
    private Customer customer;
    private double balance;
    private List<Transaction> transactions;
    private String accountStatus; // Active, Closed
    
    public Account(String accountNumber, Customer customer, double initialBalance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
        this.accountStatus = "Active";
        if (initialBalance > 0) {
            addTransaction(new Transaction(UUID.randomUUID().toString(), LocalDateTime.now(), "Deposit", initialBalance, "Initial Deposit"));
        }
    }
    
    public String getAccountNumber() { return accountNumber; }
    public Customer getCustomer() { return customer; }
    public double getBalance() { return balance; }
    public List<Transaction> getTransactions() { return new java.util.ArrayList<>(transactions); }
    public void clearTransactions() { this.transactions.clear(); }
    public void addTransactionToHistory(Transaction t) { this.transactions.add(t); }
    public void removeLastTransaction() { if (!this.transactions.isEmpty()) this.transactions.remove(this.transactions.size() - 1); }
    public String getAccountStatus() { return accountStatus; }
    
    protected void setBalance(double balance) { this.balance = balance; }
    public void setAccountStatus(String accountStatus) { this.accountStatus = accountStatus; }
    
    public void deposit(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        this.balance += amount;
        addTransaction(new Transaction(UUID.randomUUID().toString(), LocalDateTime.now(), "Deposit", amount, "Cash Deposit"));
    }
    
    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        if (this.balance < amount) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
        }
        this.balance -= amount;
        addTransaction(new Transaction(UUID.randomUUID().toString(), LocalDateTime.now(), "Withdrawal", amount, "Cash Withdrawal"));
    }
    
    protected void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
    
    public abstract void calculateInterest();
    public abstract String getAccountType();
}
