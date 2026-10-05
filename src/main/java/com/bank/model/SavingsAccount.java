package com.bank.model;
import java.time.LocalDateTime;
import java.util.UUID;

public class SavingsAccount extends Account {
    private double interestRate;
    public double getInterestRate() { return interestRate; }

    
    public SavingsAccount(String accountNumber, Customer customer, double initialBalance, double interestRate) {
        super(accountNumber, customer, initialBalance);
        this.interestRate = interestRate;
    }
    
    @Override
    public void calculateInterest() {
        double interest = getBalance() * (interestRate / 100.0);
        setBalance(getBalance() + interest);
        addTransaction(new Transaction(UUID.randomUUID().toString(), LocalDateTime.now(), "Interest", interest, "Interest Added"));
    }
    
    @Override
    public String getAccountType() {
        return "Savings";
    }
}
