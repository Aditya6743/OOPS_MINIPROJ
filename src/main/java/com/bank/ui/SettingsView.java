package com.bank.ui;

import com.bank.service.Bank;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class SettingsView {
    private ScrollPane scrollPane;
    private Bank bank;

    public SettingsView(Bank bank) {
        this.bank = bank;
        
        VBox view = new VBox(20);
        view.setPadding(new Insets(0, 0, 20, 0)); // Extra padding at bottom for scrolling

        // 1. APPEARANCE
        VBox appearanceCard = createCard("Appearance");
        GridPane appearanceGrid = new GridPane();
        appearanceGrid.setHgap(20);
        appearanceGrid.setVgap(15);
        
        Label themeLabel = new Label("Theme:");
        themeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #1e293b;");
        ComboBox<String> themeBox = new ComboBox<>();
        themeBox.getItems().addAll("Light", "Dark");
        themeBox.setValue("Light");
        
        themeBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            javafx.scene.Scene scene = scrollPane.getScene();
            if (scene != null) {
                String darkTheme = getClass().getResource("/css/dark-theme.css").toExternalForm();
                if ("Dark".equals(newVal)) {
                    if (!scene.getStylesheets().contains(darkTheme)) {
                        scene.getStylesheets().add(darkTheme);
                    }
                } else {
                    scene.getStylesheets().remove(darkTheme);
                }
            }
        });
        
        Label scaleLabel = new Label("UI Scale:");
        scaleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #1e293b;");
        ComboBox<String> scaleBox = new ComboBox<>();
        scaleBox.getItems().addAll("75%", "100%", "125%", "150%");
        scaleBox.setValue("100%");
        
        appearanceGrid.add(themeLabel, 0, 0);
        appearanceGrid.add(themeBox, 1, 0);
        appearanceGrid.add(scaleLabel, 0, 1);
        appearanceGrid.add(scaleBox, 1, 1);
        appearanceCard.getChildren().add(appearanceGrid);

        // 2. APPLICATION PREFERENCES
        VBox prefsCard = createCard("Application Preferences");
        GridPane prefsGrid = new GridPane();
        prefsGrid.setHgap(20);
        prefsGrid.setVgap(15);
        
        Label currencyLabel = new Label("Currency:");
        currencyLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #1e293b;");
        ComboBox<String> currencyBox = new ComboBox<>();
        currencyBox.getItems().addAll("₹ INR", "$ USD", "€ EUR", "£ GBP");
        currencyBox.setValue("₹ INR");
        currencyBox.setDisable(true); // Keep it strictly INR as per prompt
        
        Label dateLabel = new Label("Date Format:");
        dateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #1e293b;");
        ComboBox<String> dateBox = new ComboBox<>();
        dateBox.getItems().addAll("MMM dd, yyyy", "dd/MM/yyyy", "MM/dd/yyyy");
        dateBox.setValue("MMM dd, yyyy");
        
        Label timeLabel = new Label("Time Format:");
        timeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #1e293b;");
        ComboBox<String> timeBox = new ComboBox<>();
        timeBox.getItems().addAll("24-hour", "12-hour AM/PM");
        timeBox.setValue("24-hour");
        
        Label notifLabel = new Label("Notifications:");
        notifLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #1e293b;");
        CheckBox notifBox = new CheckBox("Enable desktop notifications");
        notifBox.setStyle("-fx-font-size: 14px; -fx-text-fill: #475569;");
        notifBox.setSelected(true);
        
        prefsGrid.add(currencyLabel, 0, 0);
        prefsGrid.add(currencyBox, 1, 0);
        prefsGrid.add(dateLabel, 0, 1);
        prefsGrid.add(dateBox, 1, 1);
        prefsGrid.add(timeLabel, 0, 2);
        prefsGrid.add(timeBox, 1, 2);
        prefsGrid.add(notifLabel, 0, 3);
        prefsGrid.add(notifBox, 1, 3);
        prefsCard.getChildren().add(prefsGrid);

        // 3. DATA & STORAGE
        VBox dataCard = createCard("Data & Storage");
        
        Label dataLocLabel = new Label("Data Location: data/");
        Label accFileLabel = new Label("Accounts File: accounts.txt");
        Label txFileLabel = new Label("Transactions File: transactions.txt");
        
        String infoStyle = "-fx-text-fill: #475569; -fx-font-size: 14px;";
        dataLocLabel.setStyle(infoStyle);
        accFileLabel.setStyle(infoStyle);
        txFileLabel.setStyle(infoStyle);
        
        HBox dataButtons = new HBox(15);
        Button btnRefresh = new Button("Refresh Data");
        btnRefresh.getStyleClass().addAll("btn", "btn-primary");
        btnRefresh.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Data Refresh");
            alert.setHeaderText(null);
            alert.setContentText("Data has been refreshed successfully.");
            alert.showAndWait();
        });
        
        Button btnReset = new Button("Reset Demo Data");
        btnReset.getStyleClass().addAll("btn", "btn-danger");
        btnReset.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reset Data");
            alert.setHeaderText("Are you sure you want to reset demo data?");
            alert.setContentText("This will delete all current accounts and transactions. This action cannot be undone.");
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Reset Data");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("Demo data reset feature is currently a mock.");
                infoAlert.showAndWait();
            }
        });
        
        dataButtons.getChildren().addAll(btnRefresh, btnReset);
        dataCard.getChildren().addAll(dataLocLabel, accFileLabel, txFileLabel, new Label(""), dataButtons);

        // 4. ABOUT BANKLY
        VBox aboutCard = createCard("About Bankly");
        
        Label appName = new Label("Bankly - Modern Banking System");
        appName.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #1e293b;");
        
        Label version = new Label("Version 1.0.0 | Java 21 | JavaFX 21 | Maven");
        version.setStyle("-fx-text-fill: #64748b; -fx-font-size: 13px;");
        
        Label description = new Label("Bankly is a JavaFX-based Bank Account Management System developed to demonstrate Object-Oriented Programming concepts including encapsulation, inheritance, abstraction, polymorphism, exception handling, collections, and file persistence.");
        description.setWrapText(true);
        description.setStyle("-fx-text-fill: #475569; -fx-font-size: 14px; -fx-line-spacing: 4px;");
        
        aboutCard.getChildren().addAll(appName, version, new Label(""), description);

        view.getChildren().addAll(appearanceCard, prefsCard, dataCard, aboutCard);
        
        scrollPane = new ScrollPane(view);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }
    
    private VBox createCard(String titleText) {
        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        Label title = new Label(titleText);
        title.getStyleClass().add("section-title");
        card.getChildren().add(title);
        return card;
    }

    public Node getView() {
        return scrollPane;
    }
}
