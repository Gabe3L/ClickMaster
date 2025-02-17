package com.gabelynch.clickmaster;

import java.awt.AWTException;
import java.awt.event.InputEvent;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AutoClicker extends Application {
    private AutoClickerBackend backend;
    private boolean isDarkMode = true;
    private Label statusLabel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            backend = new AutoClickerBackend();
        } catch (AWTException e) {
            showAlert("Error", "Failed to initialize AutoClicker backend.");
            return;
        }

        primaryStage.setTitle("AutoClicker");

        Label title = new Label("ClickMaster");
        title.setFont(new Font("Arial", 24));
        title.setTextFill(Color.web("#8e44ad"));

        TextField intervalField = new TextField();
        intervalField.setPromptText("Interval (ms)");
        intervalField.setStyle("-fx-background-radius: 15px; -fx-padding: 5px;");

        ChoiceBox<String> buttonChoice = new ChoiceBox<>();
        buttonChoice.getItems().addAll("Left Click", "Right Click");
        buttonChoice.setValue("Left Click");

        Button startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-background-radius: 15px;");
        startButton.setOnAction(e -> {
            try {
                int interval = Integer.parseInt(intervalField.getText());
                int button = buttonChoice.getValue().equals("Left Click") ? InputEvent.BUTTON1_DOWN_MASK : InputEvent.BUTTON3_DOWN_MASK;
                backend.startClicking(interval, button);
                statusLabel.setText("Status: Running");
            } catch (NumberFormatException ex) {
                showAlert("Invalid Interval", "Please enter a valid number for the interval.");
            }
        });

        Button stopButton = new Button("Stop");
        stopButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 15px;");
        stopButton.setOnAction(e -> {
            backend.stopClicking();
            statusLabel.setText("Status: Stopped");
        });

        statusLabel = new Label("Status: Stopped");
        statusLabel.setTextFill(Color.web("#ecf0f1"));

        ToggleButton themeToggle = new ToggleButton("Toggle Theme");
        themeToggle.setOnAction(e -> toggleTheme(primaryStage));

        HBox buttons = new HBox(10, startButton, stopButton);
        buttons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, intervalField, buttonChoice, buttons, statusLabel, themeToggle);

        Scene scene = new Scene(layout, 350, 300);
        applyTheme(scene);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F6) {
                if (backend.isClicking()) {
                    backend.stopClicking();
                    statusLabel.setText("Status: Stopped");
                } else {
                    try {
                        int interval = Integer.parseInt(intervalField.getText());
                        int button = buttonChoice.getValue().equals("Left Click") ? InputEvent.BUTTON1_DOWN_MASK : InputEvent.BUTTON3_DOWN_MASK;
                        backend.startClicking(interval, button);
                        statusLabel.setText("Status: Running");
                    } catch (NumberFormatException ex) {
                        showAlert("Invalid Interval", "Please enter a valid number for the interval.");
                    }
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void toggleTheme(Stage primaryStage) {
        isDarkMode = !isDarkMode;
        applyTheme(primaryStage.getScene());
    }

    private void applyTheme(Scene scene) {
        if (isDarkMode) {
            scene.setFill(Color.BLACK);
            scene.getRoot().setStyle("-fx-background-color: #2c3e50; -fx-text-fill: #ecf0f1;");
        } else {
            scene.setFill(Color.WHITE);
            scene.getRoot().setStyle("-fx-background-color: #ecf0f1; -fx-text-fill: #2c3e50;");
        }
    }
}
