package nl.tudelft.contextproject.desktop;

import nl.tudelft.contextproject.imageprocessing.ImageProcessing;

public class ImageProcessingThread implements Runnable {
    private volatile boolean running = true;
    protected ImageProcessing imageProcessingInstance;

    public ImageProcessingThread() {
        imageProcessingInstance = new ImageProcessing();
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
        imageProcessingInstance.cleanUp();
    }
}
