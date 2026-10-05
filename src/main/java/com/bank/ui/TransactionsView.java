package com.bank.ui;

import com.bank.model.Transaction;
import com.bank.service.Bank;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class TransactionsView {
    private VBox view;

    public TransactionsView(Bank bank) {
        view = new VBox(20);
        
        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        
        Label title = new Label("Transaction History");
        title.getStyleClass().add("section-title");
        
        TableView<Transaction> table = new TableView<>();
        
        TableColumn<Transaction, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        typeCol.setPrefWidth(120);
        
        TableColumn<Transaction, String> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(String.format("₹%,.2f", cellData.getValue().getAmount())));
        amountCol.setPrefWidth(120);
        
        TableColumn<Transaction, String> descCol = new TableColumn<>("Source/Destination");
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        descCol.setPrefWidth(200);

        TableColumn<Transaction, String> dateCol = new TableColumn<>("Timestamp");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss");
        dateCol.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDate().format(formatter)));
        dateCol.setPrefWidth(180);
        
        TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(cellData -> new SimpleStringProperty("Completed"));
        statusCol.setPrefWidth(100);
        
        table.getColumns().addAll(typeCol, amountCol, descCol, dateCol, statusCol);
        table.setItems(FXCollections.observableArrayList(bank.getAllTransactions()));
        table.setPrefHeight(450);
        javafx.scene.layout.VBox.setVgrow(table, javafx.scene.layout.Priority.ALWAYS);
        javafx.scene.layout.VBox.setVgrow(card, javafx.scene.layout.Priority.ALWAYS);
        
        card.getChildren().addAll(title, table);
        view.getChildren().add(card);
        javafx.scene.layout.VBox.setVgrow(view, javafx.scene.layout.Priority.ALWAYS);
    }

    public VBox getView() {
        return view;
    }
}
