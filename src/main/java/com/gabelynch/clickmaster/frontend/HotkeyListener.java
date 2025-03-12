package com.gabelynch.clickmaster.frontend;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.gabelynch.clickmaster.backend.DatabaseManager;

public class HotkeyListener extends KeyAdapter {
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    private boolean isRunning;

    public HotkeyListener() {
        this.isRunning = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F6) {
            isRunning = !isRunning;
            logger.log(Level.FINEST, (isRunning ? "AutoClicker started" : "AutoClicker stopped"));
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
