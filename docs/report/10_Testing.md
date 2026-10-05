# Testing

The system employs structural and functional testing for all modules.
Edge cases verified include:
- Attempting to withdraw beyond the overdraft limit.
- Transferring funds to the same account.
- Inputting negative values for deposits.
- Searching for non-existent account numbers.
All operations verify that rollback mechanisms work (e.g., in `transfer()`) and that the file state accurately mirrors memory state.
