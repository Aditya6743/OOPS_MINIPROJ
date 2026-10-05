# Class Diagram Documentation

The UML class diagram (`class-diagram.puml`) illustrates the primary object-oriented design of the Bank Account Management System.

## Relationships

1. **Inheritance (IS-A):**
   - `SavingsAccount` extends `Account`.
   - `CurrentAccount` extends `Account`.
   Both concrete classes implement the abstract methods defined in `Account`, such as `getAccountType()`. `CurrentAccount` also overrides the `withdraw()` method to integrate overdraft checking.

2. **Composition/Aggregation (HAS-A):**
   - **Account to Customer:** An `Account` holds a reference to a `Customer` object, establishing a one-to-one mapping in the object scope (a customer owns the account).
   - **Account to Transaction:** An `Account` contains a `List<Transaction>`. This represents a strong composition (or aggregation) where the transaction history belongs entirely to the specific account instance.
   - **Bank to Account:** The `Bank` service aggregates all `Account` entities in a generic `List<Account>`.

3. **Dependency (USES):**
   - The `Bank` service depends on `FileManager` to serialize and deserialize the state of the accounts list to the disk.
