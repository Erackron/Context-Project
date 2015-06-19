package nl.tudelft.contextproject.imageprocessing;


import nl.tudelft.contextproject.imageprocessing.framehandlers.FrameHandler;
import nl.tudelft.contextproject.imageprocessing.gui.CameraSelectDialog;
import org.opencv.core.Core;
import org.opencv.highgui.VideoCapture;

public class ImageProcessing {
    protected FrameHandler frameHandler;
    protected CameraSelectDialog cameraSelectWindow;
    protected VideoCapture videoCapture = null;

    static {
        nu.pattern.OpenCV.loadShared();
        System.out.printf("Using OpenCV version %s%n", Core.VERSION);
    }

    /**
     * Create a new ImageProcessing instance.
     */
    public ImageProcessing() throws Exception {
        cameraSelectWindow = new CameraSelectDialog();
        cameraSelectWindow.selectCamera(camera -> videoCapture = camera);

        if (videoCapture == null) {
            throw new Exception("No camera selected. Exiting");
        }


        if (!videoCapture.isOpened()) {
            throw new Exception("Unable to open the camera. Exiting");
        }

        frameHandler = new FrameHandler(videoCapture);
    }

    /**
     * The main image processing loop.
     * This should be called every image processing cycle until the end of the program
     */
    public void loop() throws Exception {
        frameHandler.loop();
    }

    /**
     * Called when the ImageProcessing thread is stopping to enable cleaning up created thins.
     */
    public void cleanUp() {
        frameHandler.cleanUp();
    }
}
