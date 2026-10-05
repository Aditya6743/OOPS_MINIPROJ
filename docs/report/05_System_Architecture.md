# System Architecture

The application follows a layered architectural pattern separating the user interface from business logic and data persistence:

1. **Presentation Layer (UI Views):** Consists of Java Swing/AWT classes (e.g., `DashboardView`, `DepositView`, `TransferView`). It captures user inputs and renders outputs.
2. **Service Layer (`Bank.java`):** Acts as the core controller. It processes business rules, manages the lists of accounts, orchestrates transactions, and validates operations.
3. **Domain/Model Layer:** Contains POJOs and business entities (`Customer`, `Account`, `SavingsAccount`, `CurrentAccount`, `Transaction`) embodying state and specific behaviors (like `calculateInterest()`).
4. **Data Access Layer (`FileManager.java`):** Handles serialization and deserialization of objects to and from flat text files (`data/accounts.txt` and `data/transactions.txt`).

**Flow Example:**
`UI View` -> calls method on `Bank Service` -> applies logic on `Model` -> calls `FileManager` -> writes to `.txt`
