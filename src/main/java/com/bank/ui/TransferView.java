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
        
        VBox card = new VBox(20);
        card.getStyleClass().add("card");
        card.setMaxWidth(500);
        
        Label title = new Label("Transfer Funds");
        title.getStyleClass().add("section-title");
        
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(20);
        
        Label fromLabel = new Label("From Account:");
        fromLabel.getStyleClass().add("label-text");
        TextField fromField = new TextField();
        fromField.getStyleClass().add("text-field");
        fromField.setPromptText("Source account number");
        
        Label toLabel = new Label("To Account:");
        toLabel.getStyleClass().add("label-text");
        TextField toField = new TextField();
        toField.getStyleClass().add("text-field");
        toField.setPromptText("Destination account number");
        
        Label amountLabel = new Label("Amount (₹):");
        amountLabel.getStyleClass().add("label-text");
        TextField amountField = new TextField();
        amountField.getStyleClass().add("text-field");
        amountField.setPromptText("Enter amount to transfer");
        
        Button transferBtn = new Button("Confirm Transfer");
        transferBtn.getStyleClass().addAll("btn", "btn-primary");
        transferBtn.setPrefWidth(150);
        
        transferBtn.setOnAction(e -> {
            try {
                String from = fromField.getText();
                String to = toField.getText();
                if (from == null || from.trim().isEmpty() || to == null || to.trim().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Account numbers cannot be empty.");
                    return;
                }
                double amt = Double.parseDouble(amountField.getText());
                bank.transfer(from, to, amt);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Transfer successful!");
                fromField.clear();
                toField.clear();
                amountField.clear();
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid amount format.");
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });
        
        form.add(fromLabel, 0, 0);
        form.add(fromField, 1, 0);
        form.add(toLabel, 0, 1);
        form.add(toField, 1, 1);
        form.add(amountLabel, 0, 2);
        form.add(amountField, 1, 2);
        form.add(transferBtn, 1, 3);
        
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
