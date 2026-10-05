package com.bank.ui;

import com.bank.model.Account;
import com.bank.service.Bank;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.function.Consumer;

public class CreateAccountView {
    private VBox view;

    public CreateAccountView(Bank bank, Consumer<String> navigation) {
        view = new VBox(20);
        
        VBox card = new VBox(20);
        card.getStyleClass().add("card");
        card.setMaxWidth(500);
        
        Label title = new Label("Create New Account");
        title.getStyleClass().add("section-title");
        
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(20);
        
        Label nameLabel = new Label("Customer Name:");
        nameLabel.getStyleClass().add("label-text");
        TextField nameField = new TextField();
        nameField.getStyleClass().add("text-field");
        nameField.setPromptText("Enter full name");
        
        Label idLabel = new Label("Customer ID:");
        idLabel.getStyleClass().add("label-text");
        TextField idField = new TextField();
        idField.getStyleClass().add("text-field");
        idField.setPromptText("e.g. C001");
        
        Label typeLabel = new Label("Account Type:");
        typeLabel.getStyleClass().add("label-text");
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Savings", "Current");
        typeBox.setValue("Savings");
        typeBox.getStyleClass().add("combo-box");
        
        Label depositLabel = new Label("Initial Deposit (₹):");
        depositLabel.getStyleClass().add("label-text");
        TextField depositField = new TextField();
        depositField.getStyleClass().add("text-field");
        depositField.setPromptText("Enter initial amount");
        
        Button createBtn = new Button("Create Account");
        createBtn.getStyleClass().addAll("btn", "btn-primary");
        createBtn.setPrefWidth(200);
        
        createBtn.setOnAction(e -> {
            try {
                String name = nameField.getText();
                String id = idField.getText();
                String type = typeBox.getValue();
                
                if (name == null || name.trim().isEmpty() || id == null || id.trim().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
                    return;
                }
                
                double initialDeposit = Double.parseDouble(depositField.getText());
                if (initialDeposit < 0) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Initial deposit cannot be negative.");
                    return;
                }
                
                Account acc = null;
                if ("Savings".equals(type)) {
                    acc = bank.createSavingsAccount(id, name, initialDeposit);
                } else {
                    acc = bank.createCurrentAccount(id, name, initialDeposit);
                }
                
                showAlert(Alert.AlertType.INFORMATION, "Success", "Account created successfully!\nAccount Number: " + acc.getAccountNumber());
                
                nameField.clear();
                idField.clear();
                depositField.clear();
                
                navigation.accept("Dashboard"); // Navigate back or to Accounts
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Invalid deposit amount format.");
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            } catch (Exception ex) {
                showAlert(Alert.AlertType.ERROR, "Error", "Could not create account: " + ex.getMessage());
            }
        });
        
        form.add(nameLabel, 0, 0);
        form.add(nameField, 1, 0);
        form.add(idLabel, 0, 1);
        form.add(idField, 1, 1);
        form.add(typeLabel, 0, 2);
        form.add(typeBox, 1, 2);
        form.add(depositLabel, 0, 3);
        form.add(depositField, 1, 3);
        form.add(createBtn, 1, 4);
        
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
