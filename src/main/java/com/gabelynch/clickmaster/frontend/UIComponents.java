package com.gabelynch.clickmaster.frontend;

import com.gabelynch.clickmaster.Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UIComponents {
    private final Main main;

    public UIComponents(Main main) {
        this.main = main;
    }

    public Label createTitleLabel() {
        Label title = new Label("ClickMaster");
        title.setFont(UIConfig.TITLE_FONT);
        title.setTextFill(UIConfig.TITLE_COLOUR);
        return title;
    }

    public TextField createTextField(String promptText) {
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        return textField;
    }

    public ChoiceBox<String> createButtonChoiceBox() {
        ChoiceBox<String> buttonChoice = new ChoiceBox<>();
        buttonChoice.getItems().addAll("Left Click", "Middle Click", "Right Click");
        buttonChoice.setValue("Left Click");
        return buttonChoice;
    }

    public Button createButton(String text, Runnable action) {
        Button button = new Button(text);
        button.setOnAction(e -> action.run());
        return button;
    }

    public ToggleButton createThemeToggle(Stage primaryStage, boolean themeStatus) {
        ToggleButton themeToggle = new ToggleButton("Toggle Theme");
        themeToggle.setOnAction(e -> toggleTheme(primaryStage, themeStatus));
        return themeToggle;
    }

    public void toggleTheme(Stage primaryStage, boolean isDarkMode) {
        main.setDarkMode(!main.isDarkMode());
        
        String themeStyle = main.isDarkMode() ? UIConfig.DARK_BACKGROUND_COLOUR : UIConfig.LIGHT_BACKGROUND_COLOUR;
        primaryStage.getScene().getRoot().setStyle(themeStyle);
    }

    public HBox createButtonBox(Button startButton, Button stopButton) {
        HBox buttons = new HBox(10, startButton, stopButton);
        buttons.setAlignment(Pos.CENTER);
        return buttons;
    }

    public VBox createLayout(Label title, TextField intervalField, TextField xField, TextField yField,
                             ChoiceBox<String> buttonChoice, HBox buttons, ToggleButton themeToggle) {
        VBox layout = new VBox(15, title, intervalField, xField, yField, buttonChoice, buttons, themeToggle);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        return layout;
    }
}
