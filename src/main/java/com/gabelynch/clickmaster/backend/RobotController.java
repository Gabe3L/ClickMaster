package com.gabelynch.clickmaster.backend;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RobotController {
    private static boolean isClicking = false;
    private static Robot robot;
    private static ScheduledExecutorService executorService;

    static {
        try {
            robot = new Robot();
            executorService = Executors.newSingleThreadScheduledExecutor();
        } catch (AWTException e) {
            System.err.println("Error initializing robot: " + e.getMessage());
        }
    }

    public static void startClicking(int interval, int button, int x, int y) {
        if (!isClicking) {
            isClicking = true;

            if (executorService.isShutdown()) {
                executorService = Executors.newSingleThreadScheduledExecutor();
            }

            executorService.scheduleAtFixedRate(() -> {
                try {
                    final int currentX = (x == -1) ? MouseInfo.getPointerInfo().getLocation().x : x;
                    final int currentY = (y == -1) ? MouseInfo.getPointerInfo().getLocation().y : y;

                    robot.mouseMove(currentX, currentY);
                    robot.mousePress(button);
                    robot.mouseRelease(button);
                } catch (NullPointerException e) {
                    System.err.println("Robot object is not initialized: " + e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.err.println("Invalid argument provided for mouse movement or click: " + e.getMessage());
                }
            }, 0, interval, TimeUnit.MILLISECONDS); 
        }
    }

    public static void stopClicking() {
        isClicking = false;
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }

    public static int getButtonFromChoice(String buttonChoice) {
        return switch (buttonChoice) {
            case "Middle Click" -> InputEvent.BUTTON2_DOWN_MASK;
            case "Right Click" -> InputEvent.BUTTON3_DOWN_MASK;
            default -> InputEvent.BUTTON1_DOWN_MASK;
        };
    }
}
