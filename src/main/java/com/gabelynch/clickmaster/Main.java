package com.gabelynch.clickmaster;

import com.gabelynch.clickmaster.backend.RobotController;
import com.gabelynch.clickmaster.frontend.UIComponents;
import com.gabelynch.clickmaster.frontend.UIConfig;

import javafx.application.Application;
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

public class Main extends Application {
    private final UIComponents uiComponents = new UIComponents(this);
    private boolean isDarkMode = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("AutoClicker");

        // UI components
        Label title = uiComponents.createTitleLabel();
        TextField intervalField = uiComponents.createTextField("Interval (ms)");
        TextField xField = uiComponents.createTextField("X Coordinate");
        TextField yField = uiComponents.createTextField("Y Coordinate");
        ChoiceBox<String> buttonChoice = uiComponents.createButtonChoiceBox();
        
        Button startButton = uiComponents.createButton("Start", () -> startClicking(intervalField, buttonChoice, xField, yField));
        Button stopButton = uiComponents.createButton("Stop", RobotController::stopClicking);

        ToggleButton themeToggle = uiComponents.createThemeToggle(primaryStage, isDarkMode);

        // Layout
        HBox buttons = uiComponents.createButtonBox(startButton, stopButton);
        VBox layout = uiComponents.createLayout(title, intervalField, xField, yField, buttonChoice, buttons, themeToggle);

        // Scene Setup
        Scene scene = new Scene(layout, UIConfig.SCREEN_WIDTH, UIConfig.SCREEN_HEIGHT);
        applyTheme(scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startClicking(TextField intervalField, ChoiceBox<String> buttonChoice, TextField xField, TextField yField) {
        try {
            int interval = Integer.parseInt(intervalField.getText().trim());
            int button = RobotController.getButtonFromChoice(buttonChoice.getValue());
            int x = parseCoordinate(xField);
            int y = parseCoordinate(yField);

            RobotController.startClicking(interval, button, x, y);
        } catch (NumberFormatException ex) {
            showAlert("Invalid Interval", "Please enter a valid number for the interval.");
        }
    }

    private int parseCoordinate(TextField field) {
        return field.getText().trim().isEmpty() ? -1 : Integer.parseInt(field.getText().trim());
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void applyTheme(Scene scene) {
        scene.getRoot().setStyle(isDarkMode ? UIConfig.DARK_BACKGROUND_COLOUR : UIConfig.LIGHT_BACKGROUND_COLOUR);
    }
    
    public boolean isDarkMode() {
        return isDarkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.isDarkMode = darkMode;
    }
}
