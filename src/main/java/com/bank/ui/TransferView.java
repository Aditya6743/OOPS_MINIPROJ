package com.bank.ui;

import com.bank.service.Bank;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class TransferView {
    private VBox view;

    public TransferView(Bank bank) {
        view = new VBox(20);
        
        view.setAlignment(javafx.geometry.Pos.CENTER);
        
        VBox card = new VBox(25);
        card.getStyleClass().add("card");
        card.setMaxWidth(800);
        card.setPadding(new Insets(30));
        
        Label title = new Label("Transfer Funds");
        title.getStyleClass().add("section-title");
        title.setStyle("-fx-font-size: 22px;");
        
        VBox form = new VBox(20);
        
        VBox fromGroup = new VBox(8);
        Label fromLabel = new Label("From Account");
        fromLabel.getStyleClass().add("label-text");
        TextField fromField = new TextField();
        fromField.getStyleClass().add("text-field");
        fromField.setPromptText("Source account number");
        fromGroup.getChildren().addAll(fromLabel, fromField);
        
        VBox toGroup = new VBox(8);
        Label toLabel = new Label("To Account");
        toLabel.getStyleClass().add("label-text");
        TextField toField = new TextField();
        toField.getStyleClass().add("text-field");
        toField.setPromptText("Destination account number");
        toGroup.getChildren().addAll(toLabel, toField);
        
        VBox amountGroup = new VBox(8);
        Label amountLabel = new Label("Amount (₹)");
        amountLabel.getStyleClass().add("label-text");
        TextField amountField = new TextField();
        amountField.getStyleClass().add("text-field");
        amountField.setPromptText("Enter amount to transfer");
        amountGroup.getChildren().addAll(amountLabel, amountField);
        
        Button transferBtn = new Button("Confirm Transfer");
        transferBtn.getStyleClass().addAll("btn", "btn-primary");
        transferBtn.setMaxWidth(Double.MAX_VALUE);
        transferBtn.setStyle("-fx-padding: 12 20; -fx-font-size: 15px;");
        
        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-font-weight: bold; -fx-padding: 5 0 0 0;");
        statusLabel.setWrapText(true);
        statusLabel.setMaxWidth(Double.MAX_VALUE);
        
        transferBtn.setOnAction(e -> {
            try {
                String from = fromField.getText();
                String to = toField.getText();
                if (from == null || from.trim().isEmpty() || to == null || to.trim().isEmpty()) {
                    statusLabel.setText("Error: Account numbers cannot be empty.");
                    statusLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                    return;
                }
                double amt = Double.parseDouble(amountField.getText());
                bank.transfer(from, to, amt);
                statusLabel.setText("Success: Transfer successful!");
                statusLabel.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold;");
                fromField.clear();
                toField.clear();
                amountField.clear();
            } catch (NumberFormatException ex) {
                statusLabel.setText("Error: Invalid amount format.");
                statusLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
            } catch (Exception ex) {
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
            }
        });
        
        form.getChildren().addAll(fromGroup, toGroup, amountGroup, transferBtn, statusLabel);
        
        card.getChildren().addAll(title, form);
        view.getChildren().add(card);
    }

    public VBox getView() {
        return view;
    }
}
