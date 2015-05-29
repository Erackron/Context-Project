package nl.tudelft.contextproject.imageprocessing;


import nl.tudelft.contextproject.imageprocessing.framehandlers.BlobDetectionFrameHandler;
import org.opencv.core.Core;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.FeatureDetector;
import org.opencv.highgui.VideoCapture;
import org.opencv.video.BackgroundSubtractorMOG2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ImageProcessing {
    protected BlobDetectionFrameHandler blobDetectionFrameHandler;

    static {
        nu.pattern.OpenCV.loadShared();
        System.out.printf("Using OpenCV version %s\n", Core.VERSION);
    }

    /**
     * Create a new ImageProcessing instance.
     */
    public ImageProcessing() {

        VideoCapture videoCapture = new VideoCapture(0);

        if (!videoCapture.isOpened()) {
            System.err.println("Unable to open the camera");
            System.exit(-1);
        }

        BackgroundSubtractorMOG2 bgSubtractor = new BackgroundSubtractorMOG2(500, 16, false);
        bgSubtractor.setInt("nmixtures", 3);

        // Create motion templating handler
        MatOfKeyPoint keyPoints = new MatOfKeyPoint();

        // Create blobframe handler
        FeatureDetector simpleBlobDetector = FeatureDetector.create(FeatureDetector.SIMPLEBLOB);
        loadBlobParams(simpleBlobDetector, "blobParams");

        blobDetectionFrameHandler = new BlobDetectionFrameHandler(
                videoCapture, bgSubtractor, simpleBlobDetector, keyPoints);
    }

    private void loadBlobParams(FeatureDetector simpleBlobDetector, String blobParams) {
        InputStream inputStream = ImageProcessing.class.getResourceAsStream("/" + blobParams);
        try {
            Files.copy(inputStream, new File(blobParams).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            simpleBlobDetector.read(blobParams);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * The main image processing loop.
     * This should be called every image processing cycle until the end of the program
     */
    public void loop() throws Exception {
        blobDetectionFrameHandler.loop();
    }

    /**
     * Called when the ImageProcessing thread is stopping to enbale cleaning up created thins.
     */
    public void cleanUp() {
        blobDetectionFrameHandler.cleanUp();
    }
}
