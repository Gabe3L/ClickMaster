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
        initializeBackend();

        primaryStage.setTitle("AutoClicker");

        Label title = createTitleLabel();
        TextField intervalField = createTextField("Interval (ms)");
        TextField xField = createTextField("X Coordinate");
        TextField yField = createTextField("Y Coordinate");

        ChoiceBox<String> buttonChoice = createButtonChoiceBox();

        Button startButton = createStartButton(intervalField, buttonChoice, xField, yField);
        Button stopButton = createStopButton();

        statusLabel = createStatusLabel();
        ToggleButton themeToggle = createThemeToggle(primaryStage);

        HBox buttons = createButtonBox(startButton, stopButton);
        VBox layout = createLayout(title, intervalField, xField, yField, buttonChoice, buttons, themeToggle);

        Scene scene = new Scene(layout, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        applyTheme(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeBackend() {
        try {
            backend = new AutoClickerBackend();
        } catch (AWTException e) {
            showAlert("Error", "Failed to initialize AutoClicker backend.");
        }
    }

    private Label createTitleLabel() {
        Label title = new Label("ClickMaster");
        title.setFont(Config.TITLE_FONT);
        title.setTextFill(Config.TITLE_COLOUR);
        return title;
    }

    private TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        return textField;
    }

    private ChoiceBox<String> createButtonChoiceBox() {
        ChoiceBox<String> buttonChoice = new ChoiceBox<>();
        buttonChoice.getItems().addAll("Left Click", "Middle Click", "Right Click");
        buttonChoice.setValue("Left Click");
        return buttonChoice;
    }

    private Button createStartButton(TextField intervalField, ChoiceBox<String> buttonChoice, TextField xField, TextField yField) {
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            startClicking(intervalField, buttonChoice, xField, yField);
        });
        return startButton;
    }

    private Button createStopButton() {
        Button stopButton = new Button("Stop");
        stopButton.setOnAction(e -> stopClicking());
        return stopButton;
    }

    private Label createStatusLabel() {
        Label status = new Label("Status: Stopped");
        status.setTextFill(Config.STATUS_MODE_COLOUR);
        return status;
    }

    private ToggleButton createThemeToggle(Stage primaryStage) {
        ToggleButton themeToggle = new ToggleButton("Toggle Theme");
        themeToggle.setOnAction(e -> toggleTheme(primaryStage));
        return themeToggle;
    }

    private HBox createButtonBox(Button startButton, Button stopButton) {
        HBox buttons = new HBox(10, startButton, stopButton);
        buttons.setAlignment(Pos.CENTER);
        return buttons;
    }

    private VBox createLayout(Label title, TextField intervalField, TextField xField, TextField yField, ChoiceBox<String> buttonChoice, HBox buttons, ToggleButton themeToggle) {
        VBox layout = new VBox(15, title, intervalField, xField, yField, buttonChoice, buttons, statusLabel, themeToggle);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        return layout;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void startClicking(TextField intervalField, ChoiceBox<String> buttonChoice, TextField xField, TextField yField) {
        try {
            int interval = Integer.parseInt(intervalField.getText().trim());
            int button = getButtonFromChoice(buttonChoice);
            int x = parseCoordinate(xField);
            int y = parseCoordinate(yField);

            backend.startClicking(interval, button, x, y);
            statusLabel.setText("Status: Running");
        } catch (NumberFormatException ex) {
            showAlert("Invalid Interval", "Please enter a valid number for the interval.");
        }
    }

    private void stopClicking() {
        backend.stopClicking();
        statusLabel.setText("Status: Stopped");
    }

    private int getButtonFromChoice(ChoiceBox<String> buttonChoice) {
        return switch (buttonChoice.getValue()) {
            case "Middle Click" -> InputEvent.BUTTON2_DOWN_MASK;
            case "Right Click" -> InputEvent.BUTTON3_DOWN_MASK;
            default -> InputEvent.BUTTON1_DOWN_MASK;
        };
    }

    private int parseCoordinate(TextField field) {
        return field.getText().trim().isEmpty() ? -1 : Integer.parseInt(field.getText().trim());
    }

    private void toggleTheme(Stage primaryStage) {
        isDarkMode = !isDarkMode;
        applyTheme(primaryStage.getScene());
    }

    private void applyTheme(Scene scene) {
        scene.getRoot().setStyle(isDarkMode ? Config.DARK_BACKGROUND_COLOUR : Config.LIGHT_BACKGROUND_COLOUR);
    }
}
