# File Persistence

The `FileManager` class uses Java standard I/O (`java.io.PrintWriter`, `BufferedReader`, `FileWriter`, `FileReader`) to achieve persistence.

- **Storage Location:** A local `data/` directory.
- **Files:**
  - `accounts.txt`: Stores account number, type, customer ID, customer name, balance, and account-specific extra info (interest rate or overdraft limit).
  - `transactions.txt`: Stores transaction ID, date, type, amount, description, and associated account number.
- **Lifecycle:** Data is written automatically upon any state-altering operation in the `Bank` service (`saveData()`). It is read once upon application startup (`loadData()`).
