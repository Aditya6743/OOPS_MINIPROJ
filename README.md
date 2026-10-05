# Bankly
Bank Account Management System

## Features
- Create, view, deposit, withdraw, and transfer funds between accounts
- Persistent storage using text files
- Dark and Light themes

## Technology
- Java 21
- JavaFX
- Maven

## Structure
- `src/main/java/com/bank/`: Contains model, service, ui, and exception packages
- `src/main/resources/css/`: Contains CSS stylesheets
- `data/`: Contains persistent data files (accounts.txt, transactions.txt)
- `docs/`: Contains project documentation (uml, report, presentation, screenshots)

## Running instructions
Ensure Java 21 is installed and properly configured.

```bash
export JAVA_HOME=/opt/homebrew/opt/openjdk@21
mvn clean javafx:run
```
