package com.gabelynch.clickmaster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gabelynch.clickmaster.backend.DatabaseManager;
import com.gabelynch.clickmaster.backend.RobotController;
import com.gabelynch.clickmaster.config.UIConfig;
import com.gabelynch.clickmaster.frontend.UIComponents;

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
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private final UIComponents uiComponents = new UIComponents(this);
    private boolean isDarkMode = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        DatabaseManager.initializeDatabase(); 
        DatabaseManager.loadLastSettings();
        setupScene(createUI(primaryStage), primaryStage);
    }

    private VBox createUI(Stage primaryStage) {
        Label title = uiComponents.createTitleLabel();
        TextField intervalField = uiComponents.createTextField("Interval (ms)");
        TextField xField = uiComponents.createTextField("X Coordinate");
        TextField yField = uiComponents.createTextField("Y Coordinate");
        ChoiceBox<String> buttonChoice = uiComponents.createButtonChoiceBox();
        
        Button startButton = uiComponents.createButton("Start", () -> startClicking(intervalField, buttonChoice, xField, yField));
        Button stopButton = uiComponents.createButton("Stop", RobotController::stopClicking);

        HBox buttons = uiComponents.createButtonBox(startButton, stopButton);

        ToggleButton themeToggle = uiComponents.createThemeToggle(primaryStage, isDarkMode);

        VBox layout = uiComponents.createLayout(title, intervalField, xField, yField, buttonChoice, buttons, themeToggle);

        return layout; 
    }

    private void setupScene(VBox layout, Stage primaryStage) {
        Scene scene = new Scene(layout, UIConfig.SCREEN_WIDTH, UIConfig.SCREEN_HEIGHT);
        applyTheme(scene);

        primaryStage.setTitle("AutoClicker");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startClicking(TextField intervalField, ChoiceBox<String> buttonChoice, TextField xField, TextField yField) {
        try {
            int interval = Integer.parseInt(intervalField.getText().trim());
            int button = RobotController.getButtonFromChoice(buttonChoice.getValue());
            int x = parseCoordinate(xField);
            int y = parseCoordinate(yField);

            DatabaseManager.saveSettings(interval, button, x, y);
            RobotController.startClicking(interval, button, x, y);
        } catch (NumberFormatException ex) {
            logger.error("Invalid interval entered: {}", intervalField.getText().trim(), ex);
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
        logger.debug("Theme applied: {}", isDarkMode ? "Dark" : "Light");
    }

    public boolean isDarkMode() {
        return isDarkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.isDarkMode = darkMode;
    }
}
