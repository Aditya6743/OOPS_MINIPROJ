package com.bank.ui;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.service.Bank;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

public class DashboardView {
    private Bank bank;
    private VBox view;
    private Consumer<String> navigation;

    public DashboardView(Bank bank, Consumer<String> navigation) {
        this.bank = bank;
        this.navigation = navigation;
        view = new VBox(30);
        
        // --- Stats Row ---
        HBox statsRow = new HBox(20);
        
        double totalBalance = 0;
        List<Account> accounts = bank.getAllAccounts();
        for (Account acc : accounts) {
            totalBalance += acc.getBalance();
        }
        
        if (accounts.isEmpty()) {
            VBox emptyCard = new VBox(10);
            emptyCard.getStyleClass().add("card");
            HBox.setHgrow(emptyCard, Priority.ALWAYS);
            Label emptyTitle = new Label("No accounts yet");
            emptyTitle.getStyleClass().add("card-title");
            Label emptyDesc = new Label("Create an account to get started.");
            emptyDesc.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px;");
            emptyCard.getChildren().addAll(emptyTitle, emptyDesc);
            statsRow.getChildren().add(emptyCard);
        } else {
            String formattedBalance = String.format("₹%,.2f", totalBalance);
            
            VBox balCard = createStatCard("Total Balance", formattedBalance, true);
            VBox accCard = createStatCard("Total Accounts", String.valueOf(accounts.size()), false);
            
            int activeCount = (int) accounts.stream().filter(a -> a.getAccountStatus().equals("Active")).count();
            VBox activeCard = createStatCard("Active Accounts", String.valueOf(activeCount), false);
            
            statsRow.getChildren().addAll(balCard, accCard, activeCard);
        }
        
        // --- Quick Actions ---
        VBox quickActionsCard = new VBox(15);
        quickActionsCard.getStyleClass().add("card");
        Label qaTitle = new Label("Quick Actions");
        qaTitle.getStyleClass().add("section-title");
        
        HBox qaButtons = new HBox(15);
        
        Button btnCreate = new Button("🆕 Create Account");
        btnCreate.getStyleClass().addAll("btn", "btn-primary");
        btnCreate.setOnAction(e -> navigation.accept("Create Account"));

        Button btnDeposit = new Button("➕ Deposit");
        btnDeposit.getStyleClass().addAll("btn", "btn-success");
        btnDeposit.setOnAction(e -> navigation.accept("Deposit"));
        
        Button btnWithdraw = new Button("➖ Withdraw");
        btnWithdraw.getStyleClass().addAll("btn", "btn-danger");
        btnWithdraw.setOnAction(e -> navigation.accept("Withdraw"));
        
        Button btnTransfer = new Button("💸 Transfer");
        btnTransfer.getStyleClass().addAll("btn", "btn-primary");
        btnTransfer.setOnAction(e -> navigation.accept("Transfer"));
        
        qaButtons.getChildren().addAll(btnCreate, btnDeposit, btnWithdraw, btnTransfer);
        quickActionsCard.getChildren().addAll(qaTitle, qaButtons);
        
        // --- Recent Transactions ---
        VBox recentCard = new VBox(15);
        recentCard.getStyleClass().add("card");
        Label txTitle = new Label("Recent Transactions");
        txTitle.getStyleClass().add("section-title");
        
        TableView<Transaction> table = new TableView<>();
        
        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        dateCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDate().format(formatter)));
        dateCol.setPrefWidth(120);
        
        TableColumn<Transaction, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setPrefWidth(120);
        
        TableColumn<Transaction, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.format("₹%,.2f", cellData.getValue().getAmount())));
        amountCol.setPrefWidth(120);
        
        TableColumn<Transaction, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(200);
        
        table.getColumns().addAll(dateCol, typeCol, amountCol, descCol);
        
        List<Transaction> allTx = bank.getAllTransactions();
        List<Transaction> recentTx = allTx.size() > 5 ? allTx.subList(0, 5) : allTx;
        
        if (recentTx.isEmpty()) {
            Label emptyTx = new Label("No transactions yet\nYour recent transactions will appear here.");
            emptyTx.setStyle("-fx-text-fill: #94a3b8; -fx-font-size: 14px; -fx-padding: 20 0;");
            recentCard.getChildren().addAll(txTitle, emptyTx);
        } else {
            table.setItems(FXCollections.observableArrayList(recentTx));
            table.setFixedCellSize(45);
            table.setPrefHeight(45 * 5 + 45); // 5 rows + header height
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            javafx.scene.layout.VBox.setVgrow(table, Priority.ALWAYS);
            recentCard.getChildren().addAll(txTitle, table);
        }
        
        javafx.scene.layout.VBox.setVgrow(recentCard, Priority.ALWAYS);
        
        view.getChildren().addAll(statsRow, quickActionsCard, recentCard);
    }

    private VBox createStatCard(String title, String value, boolean isBalance) {
        VBox card = new VBox(10);
        card.getStyleClass().add("card");
        HBox.setHgrow(card, Priority.ALWAYS);
        card.setMinWidth(180); // Ensure titles are never truncated
        
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().add("card-title");
        
        Label valueLabel = new Label(value);
        valueLabel.getStyleClass().add("card-value");
        valueLabel.setMinHeight(javafx.scene.layout.Region.USE_PREF_SIZE);
        
        if (isBalance) {
            card.widthProperty().addListener((obs, old, newVal) -> {
                double cardWidth = newVal.doubleValue();
                double availableWidth = cardWidth - 60; // 50px card padding + 10px label padding
                
                javafx.scene.text.Text textNode = new javafx.scene.text.Text(value);
                textNode.setFont(javafx.scene.text.Font.font(valueLabel.getFont().getFamily(), javafx.scene.text.FontWeight.BOLD, 32));
                double actualTextWidth = textNode.getLayoutBounds().getWidth();
                
                if (actualTextWidth > availableWidth && availableWidth > 0) {
                    double scale = availableWidth / actualTextWidth;
                    double newSize = Math.max(12.0, Math.floor(32.0 * scale)); 
                    valueLabel.setStyle("-fx-font-size: " + newSize + "px;");
                } else {
                    valueLabel.setStyle("-fx-font-size: 32px;");
                }
            });
        }
        
        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    public VBox getView() {
        return view;
    }
}
