# OOP Implementation

- **Abstraction:** The `Account` class is abstract, defining common attributes and abstract methods like `calculateInterest()` and `getAccountType()`.
- **Inheritance:** `SavingsAccount` and `CurrentAccount` inherit from `Account`, acquiring base banking functionality while introducing specific properties (interest rate, overdraft limit).
- **Polymorphism:** The `Bank` service handles lists of `Account` objects. Calling `withdraw()` on an `Account` reference dynamically executes the overridden method in `CurrentAccount` if it is a current account (allowing overdrafts).
- **Encapsulation:** State fields like `balance` and `transactions` are kept private or protected. Modifications occur only through defined methods (`deposit()`, `withdraw()`), preventing invalid state changes.
