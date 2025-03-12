package com.gabelynch.clickmaster.frontend;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HotkeyListener extends KeyAdapter {
    private static final Logger logger = LoggerFactory.getLogger(HotkeyListener.class);
    private boolean isRunning;

    public HotkeyListener() {
        this.isRunning = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F6) {
            isRunning = !isRunning;
            logger.debug(isRunning ? "AutoClicker started" : "AutoClicker stopped");
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
