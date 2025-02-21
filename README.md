# ClickMaster - Java AutoClicker

ClickMaster is a simple and customizable auto-clicker built in Java using JavaFX and AWT. It allows users to automate mouse clicks at a specified interval and position, with support for different mouse buttons.

## Features

- Set custom click intervals (milliseconds)
- Choose between Left, Middle, or Right mouse clicks
- Specify X and Y coordinates for precise clicking (optional)
- Simple start/stop functionality
- Light and dark theme toggle
- Built with JavaFX for a clean and modern UI

## Requirements

- Java 17
- JavaFX

## Installation & Usage

1. Clone this repository:

   ```sh
   git clone https://github.com/Gabe3L/ClickMaster.git
   cd ClickMaster
2. Compile and run the program:

    ```sh
    javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -d bin src/com/gabelynch/clickmaster/*.java
    java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp bin com.gabelynch.clickmaster.AutoClicker

## How to Use

1. Open the program.
2. Enter the click interval in milliseconds.
3. *(Optional)* Enter X and Y coordinates for clicking.
4. Select the desired mouse button:
   - **Left Click**
   - **Middle Click**
   - **Right Click**
5. Click **"Start"** to begin auto-clicking.
6. Click **"Stop"** to stop auto-clicking.
7. Use the **"Toggle Theme"** button to switch between light and dark mode.

## Future Improvements

- Custom hotkeys for starting and stopping clicks.
- Support for custom click patterns.
- Saving user settings between sessions.

## License

This project is licensed under the [MIT License](LICENSE).

---

### Enjoy automated clicking with **ClickMaster**!