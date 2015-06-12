package nl.tudelft.contextproject.desktop;

import nl.tudelft.contextproject.imageprocessing.ImageProcessing;

import java.util.HashSet;
import java.util.Set;

public class ImageProcessingThread implements Runnable {
    protected volatile boolean running = true;
    protected ImageProcessing imageProcessingInstance;
    protected Set<ExitListener> exitListeners = new HashSet<>();

    /**
     * Create a new ImageProcessing Thread.
     */
    public ImageProcessingThread() {
        try {
            imageProcessingInstance = new ImageProcessing();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            running = false;
        }
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                imageProcessingInstance.loop();
            } catch (Exception e) {
                e.printStackTrace();
                running = false;
            }
        }
        onExit();
    }

    protected void onExit() {
        if (imageProcessingInstance != null) {
            imageProcessingInstance.cleanUp();
        }
        for (ExitListener exitListener : exitListeners) {
            exitListener.onExit();
        }
    }

    /**
     * Add an exit listener to this ImageProcessing Thread.
     *
     * @param exitListener The exit listener to add
     */
    public void addExitListener(ExitListener exitListener) {
        exitListeners.add(exitListener);
    }

    /**
     * Remove an exit listener from this ImageProcessing Thread.
     *
     * @param exitListener The exit listener to remove
     */
    public void removeExitListener(ExitListener exitListener) {
        exitListeners.remove(exitListener);
    }
}
