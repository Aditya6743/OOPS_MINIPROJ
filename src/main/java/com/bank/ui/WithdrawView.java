package com.bank.ui;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidAmountException;
import com.bank.service.Bank;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class WithdrawView {
    private VBox view;

    public WithdrawView(Bank bank) {
        view = new VBox(20);
        
        VBox card = new VBox(20);
        card.getStyleClass().add("card");
        card.setMaxWidth(500);
        
        Label title = new Label("Withdraw Funds");
        title.getStyleClass().add("section-title");
        
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(20);
        
        Label accLabel = new Label("Account Number:");
        accLabel.getStyleClass().add("label-text");
        TextField accField = new TextField();
        accField.getStyleClass().add("text-field");
        accField.setPromptText("Enter account number");
        
        Label amountLabel = new Label("Amount (₹):");
        amountLabel.getStyleClass().add("label-text");
        TextField amountField = new TextField();
        amountField.getStyleClass().add("text-field");
        amountField.setPromptText("Enter amount to withdraw");
        
        Button withdrawBtn = new Button("Confirm Withdrawal");
        withdrawBtn.getStyleClass().addAll("btn", "btn-danger");
        withdrawBtn.setPrefWidth(150);
        
        withdrawBtn.setOnAction(e -> {
            try {
                String acc = accField.getText();
                if (acc == null || acc.trim().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Account number cannot be empty.");
                    return;
                }
                double amt = Double.parseDouble(amountField.getText());
                bank.withdraw(acc, amt);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Withdrawal successful!");
                accField.clear();
                amountField.clear();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid amount format.");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });
        
        form.add(accLabel, 0, 0);
        form.add(accField, 1, 0);
        form.add(amountLabel, 0, 1);
        form.add(amountField, 1, 1);
        form.add(withdrawBtn, 1, 2);
        
        card.getChildren().addAll(title, form);
        view.getChildren().add(card);
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public VBox getView() {
        return view;
    }
}
