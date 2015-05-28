package nl.tudelft.contextproject.java;

import com.badlogic.gdx.LifecycleListener;

public abstract class ExitListener implements LifecycleListener {
    @Override
    public void pause() {
        // Ignored as it is not needed for this class
    }

    @Override
    public void resume() {
        // Ignored as it is not needed for this class
    }

    @Override
    public void dispose() {
        onExit();
    }

    /**
     * Method to be called when a program exits.
     */
    public abstract void onExit();
}
