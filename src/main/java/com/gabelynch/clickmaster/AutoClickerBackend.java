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

    public void startClicking(int interval, int button) {
        if (clicking) {
            return;
        }

        clicking = true;
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            if (clicking) {
                robot.mousePress(button);
                try {
                    Thread.sleep(2);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                robot.mouseRelease(button);
            }
        }, 0, interval, TimeUnit.MILLISECONDS);
    }

    public void stopClicking() {
        clicking = false;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public boolean isClicking() {
        return clicking;
    }

    public void setClicking(boolean clicking) {
        this.clicking = clicking;
    }
}
