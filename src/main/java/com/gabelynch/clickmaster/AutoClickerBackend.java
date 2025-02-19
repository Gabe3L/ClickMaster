package com.gabelynch.clickmaster;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoClickerBackend {
    private boolean clicking = false;
    private ScheduledExecutorService executorService;
    private final Robot robot;

    public AutoClickerBackend() throws AWTException {
        robot = new Robot();
    }

    public void startClicking(int interval, int button, int x, int y) {
        if (clicking) return;

        clicking = true;
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            if (!clicking) return;
            
            if (x > 0 && y > 0) {
                robot.mouseMove(x, y);
            }
            robot.mousePress(button);

            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            robot.mouseRelease(button);
        }, 0, interval, TimeUnit.MILLISECONDS);
    }

    public void stopClicking() {
        clicking = false;
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
