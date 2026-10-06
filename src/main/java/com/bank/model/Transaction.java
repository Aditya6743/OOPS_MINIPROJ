package com.bank.model;
import java.time.LocalDateTime;

public class Transaction {
    private String transactionId;
    private LocalDateTime date;
    private String type; // Deposit, Withdrawal, Transfer In, Transfer Out
    private double amount;
    private String description;
    private String accountNumber;
    private double balanceAfter;
    
    public Transaction(String transactionId, LocalDateTime date, String type, double amount, String description, String accountNumber, double balanceAfter) {
        this.transactionId = transactionId;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.accountNumber = accountNumber;
        this.balanceAfter = balanceAfter;
    }
    
    public String getTransactionId() { return transactionId; }
    public LocalDateTime getDate() { return date; }
    public String getType() { return type; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getAccountNumber() { return accountNumber; }
    public double getBalanceAfter() { return balanceAfter; }
}
