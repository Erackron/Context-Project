package nl.tudelft.contextproject.imageprocessing.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class KeyReleasedListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent keyEvent) {
        // Stub method needed to adhere to KeyListener requirements
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        // Stub method needed to adhere to KeyListener requirements
    }

    public abstract void keyReleased(KeyEvent keyEvent);
}
