package nl.tudelft.contextproject.java;

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
            imageProcessingInstance.loop();
        }
    }
}
