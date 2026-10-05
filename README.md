# Bankly — Bank Account Management System

Bankly is a robust, desktop-based banking application built with Java 21 and JavaFX. Designed as an academic project to strictly demonstrate core Object-Oriented Programming (OOP) concepts, this system provides a highly realistic, interactive interface for managing bank accounts, performing financial transactions, and ensuring data persistence without relying on a complex external database.

## Project Overview

The primary goal of Bankly is to demonstrate a fully decoupled, three-tier architecture (Model, Service, UI) in Java. It allows users to create Savings and Current accounts, perform safe deposits, withdrawals, and transfers, and view an immutable history of all transactions. By heavily utilizing encapsulation, inheritance, polymorphism, and abstraction, the application serves as a comprehensive case study in writing clean, professional, and fault-tolerant Java code.

## Key Features

- **Account Management:** Create and manage unique `SavingsAccount` and `CurrentAccount` entities.
- **Banking Operations:** Perform Deposits, Withdrawals, and inter-account Transfers with strict financial validation.
- **Dashboard:** View high-level metrics including total active accounts and cumulative system balance.
- **Transaction Management:** Automatically log every successful operation into an immutable, timestamped transaction history.
- **File Persistence:** State is dynamically saved to local `.txt` files (`accounts.txt`, `transactions.txt`). Data safely survives application restarts.
- **Settings:** Toggle between custom Light and Dark themes dynamically using CSS.

## OOP Concepts Demonstrated

This project is built around the fundamental pillars of Object-Oriented Programming:

- **Encapsulation:** All sensitive data fields in the `Account`, `Customer`, and `Transaction` models are marked `private`. Data is safely accessed and modified solely through controlled getter and setter methods.
- **Inheritance:** The `SavingsAccount` and `CurrentAccount` classes inherit from the abstract base `Account` class, sharing core attributes like `accountNumber` and `balance` while extending specific functionalities (e.g., overdraft limits).
- **Abstraction:** The `Account` class is declared `abstract`. It defines abstract methods like `calculateInterest()` and `getAccountType()` which force child classes to provide their own specific implementations, hiding the complex backend details from the UI layer.
- **Polymorphism:** The `Bank` service layer processes transactions using the base `Account` type. Methods like `withdraw()` dynamically invoke the correct behavior depending on whether the object is a `SavingsAccount` or a `CurrentAccount` at runtime.
- **Constructors:** Parameterized constructors are utilized throughout the Model layer to safely initialize valid object states upon instantiation.
- **Method Overriding:** Child classes override the base `withdraw()` method to enforce custom rules (e.g., checking the `overdraftLimit` in `CurrentAccount` vs standard balance checks).
- **Collections:** The `Bank` service uses `List<Account>` (`ArrayList`) and `List<Transaction>` to dynamically store, filter, and retrieve records in memory.
- **Exception Handling:** Custom exception classes (`AccountNotFoundException`, `InsufficientBalanceException`, `InvalidAmountException`) are actively thrown by the business logic and cleanly caught by the UI to display user-friendly error alerts rather than crashing the system.

## Technology Stack

- **Language:** Java 21 LTS
- **GUI Framework:** JavaFX 21.0.1
- **Build Tool:** Apache Maven
- **Styling:** Custom CSS (`style.css`, `dark-theme.css`)
- **Persistence:** Custom Java File I/O (No external DB required)

## System Architecture

The application implements a decoupled architecture:
1. **Model Layer (`com.bank.model`):** Contains the data blueprints (`Account`, `SavingsAccount`, `Transaction`, etc.).
2. **Service Layer (`com.bank.service`):** Contains the core business and validation logic (`Bank`, `FileManager`).
3. **UI Layer (`com.bank.ui`):** Contains the JavaFX views (`DashboardView`, `DepositView`, etc.) that interact strictly with the Service layer.
4. **Exception Layer (`com.bank.exception`):** Custom runtime exceptions.

## Project Structure

```text
Bankly/
├── pom.xml
├── RunApp.command
├── data/
│   ├── accounts.txt
│   └── transactions.txt
├── docs/
│   ├── report/
│   │   ├── Bankly_Project_Report.md
│   │   ├── OOP_Mapping.md
│   │   └── Test_Cases.md
│   ├── screenshots/
│   │   └── SCREENSHOT_CHECKLIST.md
│   └── uml/
│       └── class-diagram.puml
└── src/
    └── main/
        ├── java/
        │   └── com/bank/
        │       ├── App.java
        │       ├── exception/
        │       ├── model/
        │       ├── service/
        │       └── ui/
        └── resources/
            └── css/
```

## Installation & Prerequisites

1. **Java Development Kit (JDK):** JDK 21 must be installed.
2. **Apache Maven:** Required to build and resolve JavaFX dependencies.
3. **macOS Environment:** This project is pre-configured with a `.command` script for macOS users.

## How to Run (macOS)

1. Clone or download the repository to your local machine.
2. Open your terminal and navigate to the root directory of the project.
3. Execute the provided run script to build and launch the application automatically:
   ```bash
   ./RunApp.command
   ```
   *Alternatively, run the Maven command manually:*
   ```bash
   export JAVA_HOME=/opt/homebrew/opt/openjdk@21
   mvn clean javafx:run
   ```

## Validation and Error Handling

The system guarantees financial integrity by validating input at the Service layer:
- **Empty/Null Input:** Safely rejected by UI forms.
- **Negative Amounts:** `InvalidAmountException` is thrown to prevent negative deposits or withdrawals.
- **Overdraft/Insufficient Funds:** `InsufficientBalanceException` protects the account limits.
- **Invalid Targets:** `AccountNotFoundException` ensures transfers only occur between existing accounts.

## Testing Performed

A comprehensive QA matrix was executed prior to release. This included boundary testing for financial inputs, object state persistence verification across application restarts, UI resizing and clipping checks, and robust exception throwing simulations. Test cases are documented in `docs/report/Test_Cases.md`.

## Documentation Locations

- **UML Class Diagram:** Located at `docs/uml/class-diagram.puml`.
- **Extensive Project Report:** Located in `docs/report/`.
- **Screenshots:** Refer to `docs/screenshots/SCREENSHOT_CHECKLIST.md` for UI capture guidelines.

## Academic Purpose

This project is submitted by:
**Aditya Tripathi**  
B.Tech Computer Science & Engineering  
Manipal University Jaipur  

It serves strictly as an educational demonstration of Object-Oriented Software Engineering and UI decoupling.

## Future Improvements

While functionally complete for its current scope, future iterations could include:
- Migration to a relational database (e.g., PostgreSQL or SQLite) via JDBC.
- User authentication and role-based access control (Admin vs Customer).
- Generation of PDF statements.
- Multi-threading for handling concurrent file I/O operations safely.

## License

This project is created for academic purposes. Do not copy or distribute without proper attribution.
