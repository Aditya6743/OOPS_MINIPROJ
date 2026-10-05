# Features and Modules

- **Dashboard:** The central hub providing navigation to all other modules.
- **Accounts:** View a complete list of registered accounts with their current balances.
- **Create Account:** Enables creation of Savings or Current accounts, linking them to a Customer ID and initial balance.
- **Deposit/Withdraw:** Allows users to add or remove funds, strictly validating against negative inputs or insufficient funds (including overdraft limits).
- **Transfer:** Facilitates moving funds between two valid accounts, ensuring atomicity (rollback is performed if the deposit phase fails).
- **Transactions:** Displays an aggregated, chronological history of all deposits, withdrawals, and transfers across accounts.
- **Settings/File Persistence:** Manages system configurations and ensures all state changes immediately invoke `FileManager` to persist data.
