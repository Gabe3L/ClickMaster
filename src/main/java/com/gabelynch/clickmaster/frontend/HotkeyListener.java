package com.gabelynch.clickmaster.frontend;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HotkeyListener extends KeyAdapter {
    private boolean isRunning;

    public HotkeyListener() {
        this.isRunning = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_F6) {
            isRunning = !isRunning;
            System.out.println(isRunning ? "AutoClicker started" : "AutoClicker stopped");
        }
    }

    public boolean isRunning() {
        return isRunning;
    }
}
