# Exception Handling

Custom exception classes inheriting from `Exception` (checked) ensure robust error propagation:
- `AccountNotFoundException`: Thrown when querying an account number that does not exist.
- `InsufficientBalanceException`: Thrown when a withdrawal or transfer exceeds the available balance (and overdraft limits).
- `InvalidAmountException`: Thrown when attempting to process negative or zero amounts.

The UI catches these exceptions to display user-friendly error dialogues instead of crashing.
