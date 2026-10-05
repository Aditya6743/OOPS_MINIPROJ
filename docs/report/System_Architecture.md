# System Architecture

The Bank Account Management System implements a multi-tier, monolithic desktop architecture.

## 1. Presentation Layer (UI)
Located in `com.bank.ui.*`. Components like `DashboardView`, `DepositView`, and `TransferView` collect input from the user and format output. They do not contain business logic; they only capture data and invoke the Service Layer.

## 2. Service Layer
Located in `com.bank.service.Bank`. This is the core orchestrator. It holds the active `accounts` list in memory. 
- Performs validation (e.g., checking if `fromAccount` equals `toAccount` during a transfer).
- Orchestrates complex actions (e.g., dual-account updates and rollback during `transfer()`).
- Pushes state changes to the Persistence layer via `saveData()`.

## 3. Domain Model Layer
Located in `com.bank.model.*`. Represents business entities (`Account`, `SavingsAccount`, `CurrentAccount`, `Customer`, `Transaction`). Encapsulates state and internal rules (e.g., `CurrentAccount`'s specific overdraft logic inside `withdraw()`).

## 4. Persistence Layer
Located in `com.bank.service.FileManager`. Responsible for converting Domain Models into CSV-formatted strings and writing them to the filesystem (`accounts.txt`, `transactions.txt`), as well as parsing them back into objects during startup.
