package com.bank;

import com.bank.service.Bank;
import com.bank.ui.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {
    private Bank bank;
    private StackPane contentArea;
    private Label headerTitle;
    private Map<String, Button> navButtons = new HashMap<>();

    @Override
    public void start(Stage stage) {
        bank = new Bank();
        
        BorderPane root = new BorderPane();
        
        // --- Sidebar ---
        VBox sidebar = new VBox(5);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(240);
        
        VBox logoContainer = new VBox();
        logoContainer.getStyleClass().add("logo-container");
        Label logo = new Label("Bankly");
        logo.getStyleClass().add("logo-text");
        logoContainer.getChildren().add(logo);
        sidebar.getChildren().add(logoContainer);
        
        String[] menuItems = {"Dashboard", "Accounts", "Create Account", "Deposit", "Withdraw", "Transfer", "Transactions", "Settings", "About"};
        String[] icons = {"🏠 ", "👥 ", "🆕 ", "➕ ", "➖ ", "💸 ", "📋 ", "⚙ ", "ℹ "};
        
        for (int i = 0; i < menuItems.length; i++) {
            String item = menuItems[i];
            Button btn = new Button(icons[i] + item);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.getStyleClass().add("sidebar-btn");
            btn.setOnAction(e -> switchView(item));
            navButtons.put(item, btn);
            sidebar.getChildren().add(btn);
        }
        
        root.setLeft(sidebar);
        
        // --- Main Content Area ---
        BorderPane mainArea = new BorderPane();
        
        // Header
        HBox header = new HBox();
        header.getStyleClass().add("header");
        header.setAlignment(Pos.CENTER_LEFT);
        
        headerTitle = new Label("Dashboard");
        headerTitle.getStyleClass().add("header-title");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label userProfile = new Label("👋 Welcome, User");
        userProfile.getStyleClass().add("user-profile");
        
        header.getChildren().addAll(headerTitle, spacer, userProfile);
        mainArea.setTop(header);
        
        // Content StackPane
        contentArea = new StackPane();
        contentArea.getStyleClass().add("content-area");
        contentArea.setPadding(new Insets(30));
        mainArea.setCenter(contentArea);
        
        root.setCenter(mainArea);
        
        switchView("Dashboard");
        
        Scene scene = new Scene(root, 1000, 700);
        try {
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        } catch (Exception e) {
            System.err.println("Could not load CSS");
        }
        
        stage.setTitle("Bankly - Modern Banking System");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();
    }

    public void switchView(String viewName) {
        headerTitle.setText(viewName);
        
        // Update nav button styles
        for (Map.Entry<String, Button> entry : navButtons.entrySet()) {
            if (entry.getKey().equals(viewName)) {
                if (!entry.getValue().getStyleClass().contains("sidebar-btn-active")) {
                    entry.getValue().getStyleClass().add("sidebar-btn-active");
                }
            } else {
                entry.getValue().getStyleClass().remove("sidebar-btn-active");
            }
        }

        contentArea.getChildren().clear();
        switch (viewName) {
            case "Dashboard": contentArea.getChildren().add(new DashboardView(bank, this::switchView).getView()); break;
            case "Accounts": contentArea.getChildren().add(new AccountsView(bank, this::switchView).getView()); break;
            case "Create Account": contentArea.getChildren().add(new CreateAccountView(bank, this::switchView).getView()); break;
            case "Deposit": contentArea.getChildren().add(new DepositView(bank).getView()); break;
            case "Withdraw": contentArea.getChildren().add(new WithdrawView(bank).getView()); break;
            case "Transfer": contentArea.getChildren().add(new TransferView(bank).getView()); break;
            case "Transactions": contentArea.getChildren().add(new TransactionsView(bank).getView()); break;
            case "Settings": contentArea.getChildren().add(new SettingsView(bank).getView()); break;
            case "About": contentArea.getChildren().add(new AboutView().getView()); break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
