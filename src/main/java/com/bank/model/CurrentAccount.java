package com.bank.model;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;
import java.time.LocalDateTime;
import java.util.UUID;

public class CurrentAccount extends Account {
    private double overdraftLimit;
    public double getOverdraftLimit() { return overdraftLimit; }

    
    public CurrentAccount(String accountNumber, Customer customer, double initialBalance, double overdraftLimit) {
        super(accountNumber, customer, initialBalance);
        this.overdraftLimit = overdraftLimit;
    }
    
    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        if (getBalance() + overdraftLimit < amount) {
            throw new InsufficientBalanceException("Overdraft limit exceeded.");
        }
        setBalance(getBalance() - amount);
        addTransaction(new Transaction(UUID.randomUUID().toString(), LocalDateTime.now(), "Withdrawal", amount, "Cash Withdrawal", getAccountNumber(), getBalance()));
    }
    
    @Override
    public void calculateInterest() {
        // Current accounts usually do not have interest
    }
    
    @Override
    public String getAccountType() {
        return "Current";
    }
}
