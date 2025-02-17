package com.gabelynch.clickmaster;

import java.awt.AWTException;
import java.awt.event.InputEvent;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AutoClicker extends Application {
    private AutoClickerBackend backend;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            backend = new AutoClickerBackend(); // Initialize backend
        } catch (AWTException e) {
            showAlert("Error", "Failed to initialize AutoClicker backend.");
            return;
        }

        primaryStage.setTitle("AutoClicker");

        TextField intervalField = new TextField();
        intervalField.setPromptText("Interval (ms)");

        ChoiceBox<String> buttonChoice = new ChoiceBox<>();
        buttonChoice.getItems().addAll("Left Click", "Right Click");
        buttonChoice.setValue("Left Click");

        // Start button
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            try {
                int interval = Integer.parseInt(intervalField.getText());
                int button = buttonChoice.getValue().equals("Left Click") ? InputEvent.BUTTON1_DOWN_MASK : InputEvent.BUTTON3_DOWN_MASK;
                backend.startClicking(interval, button);  // Backend handles the clicking
            } catch (NumberFormatException ex) {
                showAlert("Invalid Interval", "Please enter a valid number for the interval.");
            }
        });

        // Stop button
        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> {
            backend.stopClicking(); // Stop clicking
        });

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.getChildren().addAll(intervalField, buttonChoice, startButton, stopButton);

        Scene scene = new Scene(layout, 300, 200);
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
}