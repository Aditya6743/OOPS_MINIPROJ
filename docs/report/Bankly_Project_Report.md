# Bank Account Management System (Bankly) - Project Report

## Abstract
The Bank Account Management System is a Java-based desktop application designed to model fundamental banking operations. Utilizing core Object-Oriented Programming principles, the system ensures robustness, data integrity, and persistence.

## 1. Introduction
Modern banking relies on secure, automated systems to process transactions. This project emulates these core capabilities—account management, financial transactions, and auditing—in a localized environment.

## 2. Problem Statement
Manual banking processes are slow and error-prone. This software provides an automated solution enforcing strict financial rules (e.g., overdraft limits) while tracking complete transaction histories.

## 3. Objectives
- Implement secure banking transactions (Deposit, Withdraw, Transfer).
- Utilize OOP (Inheritance, Polymorphism) for modular code.
- Ensure data persistence via local text files.
- Handle edge-cases using custom Exception classes.

## 4. Technologies
- **Language:** Java
- **UI:** Java Swing / AWT
- **Persistence:** File I/O (TXT)

## 5. Architecture
UI (Views) -> Service (`Bank.java`) -> Model (Entities) -> Persistence (`FileManager.java`).

## 6. Modules and Features
- **Account Management:** Creation of Savings and Current accounts.
- **Transactions:** Core logic for moving funds, including atomic transfers.
- **History:** Aggregated view of all financial activities across accounts.

## 7. OOP Concepts
- **Abstraction:** `Account` as an abstract base class.
- **Inheritance:** `CurrentAccount` and `SavingsAccount` extending `Account`.
- **Encapsulation:** Private properties mutated only via secure methods.
- **Polymorphism:** Overriding `withdraw()` logic in `CurrentAccount` to permit overdrafts.

## 8. Persistence
Data is saved continuously to `accounts.txt` and `transactions.txt`, separated by commas (CSV-style), and re-loaded on startup.

## 9. Exception Handling
Custom exceptions (`InsufficientBalanceException`, `AccountNotFoundException`, `InvalidAmountException`) isolate business logic errors from system crashes.

## 10. Conclusion and Future Scope
The system meets its design goals. Future iterations could incorporate an RDBMS (MySQL) and a Client-Server API architecture.
