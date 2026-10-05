# Test Cases

| ID | Test Case | Input / Action | Expected Result | Actual Result | Status |
|---|---|---|---|---|---|
| TC01 | Startup / Load Data | Launch App | App reads `.txt` files and populates lists. | Data loads successfully. | Passed |
| TC02 | Create Savings Account | Valid Customer, Initial Bal: 5000 | Account created with 5000 bal. File updated. | Account created. | Passed |
| TC03 | Create Current Account | Valid Customer, Initial Bal: 1000 | Account created, default 2000 overdraft limit set. | Account created. | Passed |
| TC04 | Invalid Amount Deposit | Deposit Amount: -500 | System throws `InvalidAmountException`. | Exception caught, error shown. | Passed |
| TC05 | Withdraw Over Limit | Bal: 1000, Overdraft: 2000. Try 4000. | System throws `InsufficientBalanceException`. | Exception caught, error shown. | Passed |
| TC06 | Valid Transfer | Src: A, Dst: B, Amt: 500 | Src Bal -500, Dst Bal +500. Two Tx records made. | Balances updated, files saved. | Passed |
| TC07 | Same-Account Transfer | Src: A, Dst: A, Amt: 100 | System rejects transfer (throws exception). | Transfer rejected. | Passed |
| TC08 | Missing Account Lookup | Search for ID "XYZ999" | System throws `AccountNotFoundException`. | Exception caught, error shown. | Passed |
| TC09 | File Persistence Verify | Perform deposit, restart app. | Updated balance reflects after application restart. | Balance verified. | Passed |
