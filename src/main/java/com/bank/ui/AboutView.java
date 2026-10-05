package com.bank.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AboutView {
    private VBox view;

    public AboutView() {
        view = new VBox(20);
        view.setPadding(new Insets(30));
        view.setAlignment(Pos.TOP_LEFT);

        VBox aboutCard = new VBox(15);
        aboutCard.getStyleClass().add("card");
        
        Label title = new Label("About Bankly");
        title.getStyleClass().add("section-title");
        
        Label appName = new Label("Bankly - Modern Banking System");
        appName.setStyle("-fx-font-weight: bold; -fx-font-size: 18px; -fx-text-fill: #1e293b;");
        
        Label version = new Label("Version 1.0.0 | Java 21 | JavaFX 21 | Maven");
        version.setStyle("-fx-text-fill: #64748b; -fx-font-size: 14px;");
        
        Label description = new Label("Bankly is a JavaFX-based Bank Account Management System developed to demonstrate Object-Oriented Programming concepts including encapsulation, inheritance, abstraction, polymorphism, exception handling, collections, and file persistence.");
        description.setWrapText(true);
        description.setStyle("-fx-text-fill: #475569; -fx-font-size: 15px; -fx-line-spacing: 4px;");

        Label presentationHeader = new Label("Presented by:");
        presentationHeader.setStyle("-fx-font-weight: bold; -fx-font-size: 16px; -fx-text-fill: #1e293b; -fx-padding: 20 0 0 0;");

        Label authorName = new Label("Aditya Tripathi");
        authorName.setStyle("-fx-font-size: 15px; -fx-text-fill: #334155;");

        Label section = new Label("Section: E3");
        section.setStyle("-fx-font-size: 15px; -fx-text-fill: #334155;");

        Label registration = new Label("Registration No.: 2502050617");
        registration.setStyle("-fx-font-size: 15px; -fx-text-fill: #334155;");

        Label course = new Label("B.Tech Computer Science & Engineering");
        course.setStyle("-fx-font-size: 15px; -fx-text-fill: #334155;");

        Label university = new Label("Manipal University Jaipur");
        university.setStyle("-fx-font-size: 15px; -fx-text-fill: #334155;");

        aboutCard.getChildren().addAll(
            title, 
            appName, 
            version, 
            new Label(""), 
            description,
            presentationHeader,
            authorName,
            section,
            registration,
            course,
            university
        );

        view.getChildren().add(aboutCard);
    }

    public Node getView() {
        return view;
    }
}
