package com.bank.service;

import com.bank.model.Account;
import com.bank.model.CurrentAccount;
import com.bank.model.Customer;
import com.bank.model.SavingsAccount;
import com.bank.model.Transaction;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileManager {
    private static final String DATA_DIR = "data";
    private static final String ACCOUNTS_FILE = DATA_DIR + "/accounts.txt";
    private static final String TRANSACTIONS_FILE = DATA_DIR + "/transactions.txt";

    public FileManager() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void saveAccounts(List<Account> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ACCOUNTS_FILE))) {
            for (Account acc : accounts) {
                String type = acc.getAccountType();
                String accNum = acc.getAccountNumber();
                String custId = acc.getCustomer().getId();
                String custName = acc.getCustomer().getName().replace(",", ";");
                double balance = acc.getBalance();
                String extraInfo = "";

                if (acc instanceof SavingsAccount) {
                    extraInfo = String.valueOf(((SavingsAccount) acc).getInterestRate());
                } else if (acc instanceof CurrentAccount) {
                    extraInfo = String.valueOf(((CurrentAccount) acc).getOverdraftLimit());
                }

                writer.println(String.join(",", accNum, type, custId, custName, String.valueOf(balance), extraInfo));
            }
        } catch (IOException e) {
            System.err.println("Error saving accounts: " + e.getMessage());
        }
    }

    public void saveTransactions(List<Account> accounts) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Account acc : accounts) {
                for (Transaction t : acc.getTransactions()) {
                    writer.println(String.join(",",
                            t.getTransactionId(),
                            t.getDate().toString(),
                            t.getType(),
                            String.valueOf(t.getAmount()),
                            t.getDescription().replace(",", ";"),
                            t.getAccountNumber(),
                            String.valueOf(t.getBalanceAfter())
                    ));
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }

    public void loadData(Bank bank) {
        File accountsFile = new File(ACCOUNTS_FILE);
        File transactionsFile = new File(TRANSACTIONS_FILE);

        if (accountsFile.exists() && accountsFile.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", -1);
                    if (parts.length >= 6) {
                        try {
                            String accNum = parts[0];
                            String type = parts[1];
                            String custId = parts[2];
                            String custName = parts[3];
                            double balance = Double.parseDouble(parts[4]);
                            double extraInfo = Double.parseDouble(parts[5]);

                            Customer customer = new Customer(custId, custName);
                            Account account = null;

                            if (type.equals("Savings")) {
                                account = new SavingsAccount(accNum, customer, balance, extraInfo);
                            } else if (type.equals("Current")) {
                                account = new CurrentAccount(accNum, customer, balance, extraInfo);
                            }

                            if (account != null) {
                                // Clear transactions that were added in the constructor (e.g. initial deposit)
                                account.clearTransactions();
                                bank.addAccount(account);
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Skipping malformed account record: " + line);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading accounts: " + e.getMessage());
            }
        }

        if (transactionsFile.exists() && transactionsFile.length() > 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader(transactionsFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", -1);
                    if (parts.length >= 7) {
                        try {
                            String txId = parts[0];
                            LocalDateTime date = LocalDateTime.parse(parts[1]);
                            String type = parts[2];
                            double amount = Double.parseDouble(parts[3]);
                            String desc = parts[4];
                            String accNum = parts[5];
                            double balanceAfter = Double.parseDouble(parts[6]);

                            Transaction tx = new Transaction(txId, date, type, amount, desc, accNum, balanceAfter);
                            try {
                                Account acc = bank.findAccount(accNum);
                                acc.addTransactionToHistory(tx);
                            } catch (Exception e) {
                                System.err.println("Account not found for transaction: " + line);
                            }
                        } catch (NumberFormatException | DateTimeParseException e) {
                            System.err.println("Skipping malformed transaction record: " + line);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading transactions: " + e.getMessage());
            }
        }
    }
}
