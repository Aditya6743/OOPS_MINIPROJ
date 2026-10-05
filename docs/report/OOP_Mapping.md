# OOP Concept Mapping

| OOP Concept | Actual Code Implementation | Why it is useful |
|---|---|---|
| **Encapsulation** | `Customer`, `Transaction`, `Account` keep variables (e.g., `balance`, `transactions`) `private`. | Prevents arbitrary state modifications. Modifications must use `deposit()` or `withdraw()`. |
| **Inheritance** | `SavingsAccount extends Account`, `CurrentAccount extends Account`. | Reuses shared logic (ID, customer info, transaction history) while adding specific logic (interest, overdraft). |
| **Abstraction** | `Account` is an `abstract class`. Methods like `calculateInterest()` and `getAccountType()` are abstract. | Defines a contract that all account types must follow without providing a generic, useless implementation. |
| **Polymorphism** | `Bank` stores a `List<Account>`. Calling `acc.withdraw(amount)` invokes `CurrentAccount`'s overridden method if applicable. | Allows the service layer to process transactions uniformly without constantly checking the specific account type. |
| **Exceptions** | `InsufficientBalanceException`, `AccountNotFoundException`. | Gracefully handles business-rule violations without crashing the JVM, allowing UI layer to inform the user. |
| **Collections** | `ArrayList<Account>` in `Bank`, `ArrayList<Transaction>` in `Account`. | Dynamically scales memory to hold an arbitrary number of records. |
| **File Handling** | `FileManager` uses `PrintWriter`, `BufferedReader`, `FileWriter`. | Serializes memory objects to persistent storage (`.txt` files) ensuring state survives application restarts. |
