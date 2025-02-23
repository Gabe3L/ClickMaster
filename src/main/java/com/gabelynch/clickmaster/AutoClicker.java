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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
        title.setFont(Config.TITLE_FONT);
        title.setTextFill(Config.TITLE_COLOUR);

        TextField intervalField = new TextField();
        intervalField.setPromptText("Interval (ms)");
        
        TextField xField = new TextField();
        xField.setPromptText("X Coordinate");
        
        TextField yField = new TextField();
        yField.setPromptText("Y Coordinate");

        ChoiceBox<String> buttonChoice = new ChoiceBox<>();
        buttonChoice.getItems().addAll("Left Click", "Middle Click", "Right Click");
        buttonChoice.setValue("Left Click");

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            try {
                int interval = Integer.parseInt(intervalField.getText().trim());
                int button;
                button = switch (buttonChoice.getValue()) {
                    case "Middle Click" -> InputEvent.BUTTON2_DOWN_MASK;
                    case "Right Click" -> InputEvent.BUTTON3_DOWN_MASK;
                    default -> InputEvent.BUTTON1_DOWN_MASK;
                };
                int x = xField.getText().trim().isEmpty() ? -1 : Integer.parseInt(xField.getText().trim());
                int y = yField.getText().trim().isEmpty() ? -1 : Integer.parseInt(yField.getText().trim());
                
                backend.startClicking(interval, button, x, y);
                statusLabel.setText("Status: Running");
            } catch (NumberFormatException ex) {
                showAlert("Invalid Interval", "Please enter a valid number for the interval.");
            }
        });

        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> {
            backend.stopClicking();
            statusLabel.setText("Status: Stopped");
        });

        statusLabel = new Label("Status: Stopped");
        statusLabel.setTextFill(Config.STATUS_MODE_COLOUR);

        ToggleButton themeToggle = new ToggleButton("Toggle Theme");
        themeToggle.setOnAction(e -> toggleTheme(primaryStage));

        HBox buttons = new HBox(10, startButton, stopButton);
        buttons.setAlignment(Pos.CENTER);

        VBox layout = new VBox(15, title, intervalField, xField, yField, buttonChoice, buttons, statusLabel, themeToggle);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        applyTheme(scene);

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
            scene.getRoot().setStyle(Config.DARK_BACKGROUND_COLOUR);
        } else {
            scene.getRoot().setStyle(Config.LIGHT_BACKGROUND_COLOUR);
        }
    }
}
