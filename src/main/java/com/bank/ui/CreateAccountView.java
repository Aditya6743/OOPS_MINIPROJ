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
        
        view.setAlignment(javafx.geometry.Pos.CENTER);
        
        VBox card = new VBox(25);
        card.getStyleClass().add("card");
        card.setMaxWidth(800);
        card.setPadding(new javafx.geometry.Insets(30));
        
        Label title = new Label("Create New Account");
        title.getStyleClass().add("section-title");
        title.setStyle("-fx-font-size: 22px;");
        
        VBox form = new VBox(20);
        
        VBox nameGroup = new VBox(8);
        Label nameLabel = new Label("Customer Name");
        nameLabel.getStyleClass().add("label-text");
        TextField nameField = new TextField();
        nameField.getStyleClass().add("text-field");
        nameField.setPromptText("Enter full name");
        nameGroup.getChildren().addAll(nameLabel, nameField);
        
        VBox idGroup = new VBox(8);
        Label idLabel = new Label("Customer ID");
        idLabel.getStyleClass().add("label-text");
        TextField idField = new TextField();
        idField.getStyleClass().add("text-field");
        idField.setPromptText("e.g. C001");
        idGroup.getChildren().addAll(idLabel, idField);
        
        VBox typeGroup = new VBox(8);
        Label typeLabel = new Label("Account Type");
        typeLabel.getStyleClass().add("label-text");
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Savings", "Current");
        typeBox.setValue("Savings");
        typeBox.getStyleClass().add("combo-box");
        typeBox.setMaxWidth(Double.MAX_VALUE);
        typeGroup.getChildren().addAll(typeLabel, typeBox);
        
        VBox depositGroup = new VBox(8);
        Label depositLabel = new Label("Initial Deposit (₹)");
        depositLabel.getStyleClass().add("label-text");
        TextField depositField = new TextField();
        depositField.getStyleClass().add("text-field");
        depositField.setPromptText("Enter initial amount");
        depositGroup.getChildren().addAll(depositLabel, depositField);
        
        Button createBtn = new Button("Create Account");
        createBtn.getStyleClass().addAll("btn", "btn-primary");
        createBtn.setMaxWidth(Double.MAX_VALUE);
        createBtn.setStyle("-fx-padding: 12 20; -fx-font-size: 15px;");
        
        VBox statusBox = new VBox(8);
        statusBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        
        createBtn.setOnAction(e -> {
            statusBox.getChildren().clear();
            try {
                String name = nameField.getText();
                String id = idField.getText();
                String type = typeBox.getValue();
                
                if (name == null || name.trim().isEmpty() || id == null || id.trim().isEmpty()) {
                    Label errLabel = new Label("Error: All fields are required.");
                    errLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                    statusBox.getChildren().add(errLabel);
                    return;
                }
                
                double initialDeposit = Double.parseDouble(depositField.getText());
                if (initialDeposit < 0) {
                    Label errLabel = new Label("Error: Initial deposit cannot be negative.");
                    errLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                    statusBox.getChildren().add(errLabel);
                    return;
                }
                
                Account acc = null;
                if ("Savings".equals(type)) {
                    acc = bank.createSavingsAccount(id, name, initialDeposit);
                } else {
                    acc = bank.createCurrentAccount(id, name, initialDeposit);
                }
                
                Label succLabel = new Label("Account created successfully!");
                succLabel.setStyle("-fx-text-fill: #10b981; -fx-font-weight: bold;");
                
                TextField copyField = new TextField(acc.getAccountNumber());
                copyField.setEditable(false);
                copyField.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                
                statusBox.getChildren().addAll(succLabel, new Label("You can copy your Account Number below:"), copyField);
                
                nameField.clear();
                idField.clear();
                depositField.clear();
                
            } catch (NumberFormatException ex) {
                Label errLabel = new Label("Error: Invalid deposit amount format.");
                errLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                statusBox.getChildren().add(errLabel);
            } catch (IllegalArgumentException ex) {
                Label errLabel = new Label("Error: " + ex.getMessage());
                errLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                statusBox.getChildren().add(errLabel);
            } catch (Exception ex) {
                Label errLabel = new Label("Error: Could not create account: " + ex.getMessage());
                errLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-weight: bold;");
                statusBox.getChildren().add(errLabel);
            }
        });
        
        form.getChildren().addAll(nameGroup, idGroup, typeGroup, depositGroup, createBtn, statusBox);
        
        card.getChildren().addAll(title, form);
        view.getChildren().add(card);
    }

    public VBox getView() {
        return view;
    }
}
