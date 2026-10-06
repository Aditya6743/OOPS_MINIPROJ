package com.bank.ui;

import com.bank.model.Account;
import com.bank.service.Bank;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class AccountsView {
    private VBox view;

    public AccountsView(Bank bank, java.util.function.Consumer<String> navigation) {
        view = new VBox(20);
        
        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        
        javafx.scene.layout.HBox header = new javafx.scene.layout.HBox();
        header.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        Label title = new Label("All Accounts");
        title.getStyleClass().add("section-title");
        
        Label statusLabel = new Label("");
        statusLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-padding: 0 0 0 15;");
        
        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        javafx.scene.layout.HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        
        javafx.scene.control.Button createBtn = new javafx.scene.control.Button("🆕 Create Account");
        createBtn.getStyleClass().addAll("btn", "btn-primary");
        createBtn.setOnAction(e -> navigation.accept("Create Account"));
        
        header.getChildren().addAll(title, statusLabel, spacer, createBtn);
        
        TableView<Account> table = new TableView<>();
        
        TableColumn<Account, String> accNoCol = new TableColumn<>("Account Number");
        accNoCol.setCellValueFactory(new PropertyValueFactory<>("accountNumber"));
        accNoCol.setPrefWidth(150);
        
        TableColumn<Account, String> customerCol = new TableColumn<>("Customer Name");
        customerCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getCustomer().getName()));
        customerCol.setPrefWidth(150);
        
        TableColumn<Account, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        typeCol.setPrefWidth(100);
        
        TableColumn<Account, String> balanceCol = new TableColumn<>("Balance");
        balanceCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.format("₹%,.2f", cellData.getValue().getBalance())));
        balanceCol.setPrefWidth(150);
        
        TableColumn<Account, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("accountStatus"));
        statusCol.setPrefWidth(100);
        
        table.getColumns().addAll(accNoCol, customerCol, typeCol, balanceCol, statusCol);
        
        table.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Account> row = new javafx.scene.control.TableRow<>();
            
            javafx.scene.control.ContextMenu contextMenu = new javafx.scene.control.ContextMenu();
            javafx.scene.control.MenuItem copyItem = new javafx.scene.control.MenuItem("Copy Account Number");
            copyItem.setOnAction(event -> {
                Account acc = row.getItem();
                if (acc != null) {
                    javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
                    javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
                    content.putString(acc.getAccountNumber());
                    clipboard.setContent(content);
                    statusLabel.setText("Copied: " + acc.getAccountNumber());
                }
            });
            contextMenu.getItems().add(copyItem);
            
            row.contextMenuProperty().bind(
                javafx.beans.binding.Bindings.when(row.emptyProperty())
                .then((javafx.scene.control.ContextMenu) null)
                .otherwise(contextMenu)
            );
            
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Account acc = row.getItem();
                    javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
                    javafx.scene.input.ClipboardContent content = new javafx.scene.input.ClipboardContent();
                    content.putString(acc.getAccountNumber());
                    clipboard.setContent(content);
                    statusLabel.setText("Copied: " + acc.getAccountNumber());
                }
            });
            return row;
        });
        
        table.setItems(FXCollections.observableArrayList(bank.getAllAccounts()));
        table.setPrefHeight(450);
        javafx.scene.layout.VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
        javafx.scene.layout.VBox.setVgrow(card, javafx.scene.layout.Priority.ALWAYS);
        
        card.getChildren().addAll(header, table);
        view.getChildren().add(card);
    }

    public VBox getView() {
        return view;
    }
}
