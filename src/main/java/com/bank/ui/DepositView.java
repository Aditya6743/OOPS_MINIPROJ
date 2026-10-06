package com.bank.ui;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InvalidAmountException;
import com.bank.service.Bank;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class DepositView {
    private VBox view;

    public DepositView(Bank bank) {
        view = new VBox(20);
        
        view.setAlignment(javafx.geometry.Pos.CENTER);
        
        VBox card = new VBox(25);
        card.getStyleClass().add("card");
        card.setMaxWidth(800);
        card.setPadding(new Insets(30));
        
        Label title = new Label("Deposit Funds");
        title.getStyleClass().add("section-title");
        title.setStyle("-fx-font-size: 22px;");
        
        VBox form = new VBox(20);
        
        VBox accGroup = new VBox(8);
        Label accLabel = new Label("Account Number");
        accLabel.getStyleClass().add("label-text");
        TextField accField = new TextField();
        accField.getStyleClass().add("text-field");
        accField.setPromptText("Enter account number");
        accGroup.getChildren().addAll(accLabel, accField);
        
        VBox amountGroup = new VBox(8);
        Label amountLabel = new Label("Amount (₹)");
        amountLabel.getStyleClass().add("label-text");
        TextField amountField = new TextField();
        amountField.getStyleClass().add("text-field");
        amountField.setPromptText("Enter amount to deposit");
        amountGroup.getChildren().addAll(amountLabel, amountField);
        
        Button depositBtn = new Button("Confirm Deposit");
        depositBtn.getStyleClass().addAll("btn", "btn-success");
        depositBtn.setMaxWidth(Double.MAX_VALUE);
        depositBtn.setStyle("-fx-padding: 12 20; -fx-font-size: 15px;");
        
        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5 0 0 0;");
        statusLabel.setWrapText(true);
        statusLabel.setMaxWidth(Double.MAX_VALUE);
        
        depositBtn.setOnAction(e -> {
            try {
                String acc = accField.getText();
                if (acc == null || acc.trim().isEmpty()) {
                    statusLabel.setText("Error: Account number cannot be empty.");
                    statusLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                    return;
                }
                double amt = Double.parseDouble(amountField.getText());
                bank.deposit(acc, amt);
                statusLabel.setText("Success: Deposit successful!");
                statusLabel.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold;");
                accField.clear();
                amountField.clear();
            } catch (NumberFormatException ex) {
                statusLabel.setText("Error: Invalid amount format.");
                statusLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
            }
        });
        
        form.getChildren().addAll(accGroup, amountGroup, depositBtn, statusLabel);
        
        card.getChildren().addAll(title, form);
        view.getChildren().add(card);
    }

    public VBox getView() {
        return view;
    }
}
