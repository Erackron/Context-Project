package nl.tudelft.contextproject.imageprocessing.framehandlers;

import nl.tudelft.contextproject.imageprocessing.gui.NamedWindow;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorMOG2;

public class BlobDetectionFrameHandler {
    protected final VideoCapture capture;
    protected final BackgroundSubtractorMOG2 backgroundSubtractor;
    protected final FeatureDetector simpleBlobDetector;
    protected final MatOfKeyPoint keyPoints;
    protected final NamedWindow frameWindow;
    protected final NamedWindow backgroundWindow;

    protected Mat foreground;
    protected Mat background;
    protected Mat previous;
    protected Mat next;
    protected long start;
    protected long end;

    /**
     * Create a new BlobDetectionFrameHandler.
     *
     * @param capture            The video capture to get the frames from
     * @param bgSubtractor       The background subtractor
     * @param simpleBlobDetector The blob detector
     * @param keyPoints          A keypoint list to use for storing keypoints
     */
    public BlobDetectionFrameHandler(VideoCapture capture, BackgroundSubtractorMOG2 bgSubtractor,
                                     FeatureDetector simpleBlobDetector, MatOfKeyPoint keyPoints) {
        this.capture = capture;
        this.backgroundSubtractor = bgSubtractor;
        this.simpleBlobDetector = simpleBlobDetector;
        this.keyPoints = keyPoints;
        this.frameWindow = new NamedWindow("Frame");
        this.backgroundWindow = new NamedWindow("Background");

        this.previous = new Mat();
        this.next = new Mat();
        this.foreground = new Mat();
        this.background = new Mat();

        capture.read(previous);
    }

    /**
     * The main blob detection loop.
     */
    public void loop() throws Exception {
        start = System.currentTimeMillis();
        // Read the frame
        if (!capture.read(next)) {
            throw new Exception("Unable to open camera");
        }

        process(previous, next);

        end = System.currentTimeMillis();
        System.out.printf("duration: %.2fs\n", (end - start) / 1000f);
        previous.release();
        previous = next.clone();
    }

    protected void process(Mat previous, Mat next) {
        backgroundSubtractor.apply(next, foreground, 0.3);

        Imgproc.erode(foreground, foreground, new Mat());
        Imgproc.dilate(foreground, foreground, new Mat());

        simpleBlobDetector.detect(foreground, keyPoints);

        Features2d.drawKeypoints(next, keyPoints, next, new Scalar(0, 0, 255),
                Features2d.DRAW_RICH_KEYPOINTS);

        System.out.printf("amount of blobs: %d\n", keyPoints.total());

        frameWindow.imShow(next);
        backgroundWindow.imShow(foreground);
    }

    /**
     * Last method of the BlobDetectionFrameHandler to be called before program shutdown.
     */
    public void cleanUp() {
        frameWindow.destroyWindow();
        backgroundWindow.destroyWindow();
        capture.release();
    }
}
