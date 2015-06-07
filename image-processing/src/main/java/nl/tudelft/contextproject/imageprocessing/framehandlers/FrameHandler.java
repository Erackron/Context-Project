package nl.tudelft.contextproject.imageprocessing.framehandlers;

import nl.tudelft.contextproject.imageprocessing.gui.NamedWindow;
import nl.tudelft.contextproject.imageprocessing.listener.KeyReleasedListener;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class FrameHandler {
    protected final VideoCapture capture;
    protected final NamedWindow frameWindow;
    protected final NamedWindow foregroundWindow;
    protected final NamedWindow backgroundWindow;

    protected Mat foreground;
    protected Mat background;
    protected Mat previous;
    protected Mat current;
    protected Mat edges;
    protected long start;
    protected long end;

    /**
     * Create a new FrameHandler.
     *
     * @param capture            The video capture to get the frames from
     */
    public FrameHandler(VideoCapture capture) {
        KeyReleasedListener listener = new KeyReleasedListener() {
            @Override
            public void keyReleased(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_SPACE) {
                    setBackground();
                }
            }
        };
        this.capture = capture;
        this.frameWindow = new NamedWindow("Frame");
        this.frameWindow.setKeyListener(listener);
        this.foregroundWindow = new NamedWindow("Foreground");
        this.foregroundWindow.setKeyListener(listener);
        this.backgroundWindow = new NamedWindow("Background");
        this.backgroundWindow.setKeyListener(listener);

        this.previous = new Mat();
        this.current = new Mat();
        this.foreground = new Mat();
        this.background = new Mat();
        this.edges = new Mat();

        setBackground();
    }

    /**
     * The main detection loop.
     */
    public void loop() throws Exception {
        start = System.currentTimeMillis();
        // Read the frame
        if (!capture.read(current)) {
            throw new Exception("Unable to open camera");
        }

        process(current);

        end = System.currentTimeMillis();
        System.out.printf("duration: %.2fs\n", (end - start) / 1000f);
        previous.release();
        previous = current.clone();
    }

    /**
     * Process the current frame.
     *
     * @param current The frame to process
     */
    protected void process(Mat current) {
        backgroundSubtraction(current, foreground);

        //edgeDetection(foreground, edges);
        edges = findSegments(current, foreground);

        Core.add(current, edges, current);

        frameWindow.imShow(current);
        foregroundWindow.imShow(foreground);
        //backgroundWindow.imShow(background);
    }

    /**
     * Apply background subtraction on the current frame.
     *
     * @param current    The frame to apply background subtraction on
     * @param foreground The Mat to store the foreground in
     */
    protected void backgroundSubtraction(Mat current, Mat foreground) {
        Core.subtract(background, current, foreground);


        Imgproc.blur(foreground, foreground, new Size(4, 4));
        Imgproc.cvtColor(foreground, foreground, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(foreground, foreground, 25.0, 255.0, Imgproc.THRESH_BINARY);

    }

    /**
     * Find segments in an image.
     *
     * @param current    The image to find segments in
     * @param foreground The foreground mask (white = foreground, black = background)
     */
    protected Mat findSegments(Mat current, Mat foreground) {
        List<MatOfPoint> contours = new ArrayList<>();
        MatOfInt4 hierarchy = new MatOfInt4();
        Imgproc.findContours(foreground, contours, hierarchy, Imgproc.RETR_CCOMP,
                Imgproc.CHAIN_APPROX_SIMPLE);

        Mat segments = Mat.zeros(current.size(), CvType.CV_8UC3);
        List<MatOfPoint> contourPoly = new ArrayList<>(contours.size());

        int count = 0;
        Scalar colour = new Scalar(0, 0, 255);
        for (MatOfPoint contour : contours) {
            MatOfPoint2f contourPolyEl2f = new MatOfPoint2f();
            MatOfPoint contourPolyEl = new MatOfPoint();
            MatOfPoint2f contour2f = new MatOfPoint2f();

            contour.convertTo(contour2f, CvType.CV_32FC2);
            Imgproc.approxPolyDP(contour2f, contourPolyEl2f, 3, true);
            contourPolyEl2f.convertTo(contourPolyEl, CvType.CV_32SC2);
            contourPoly.add(contourPolyEl);

            //Rect boundingRect = Imgproc.boundingRect(contourPolyEl);
            Point centerPoint = new Point();
            float[] radiusEl = new float[1];
            Imgproc.minEnclosingCircle(contourPolyEl2f, centerPoint, radiusEl);

            Imgproc.drawContours(segments, contourPoly, count, colour, 1, Core.LINE_8, hierarchy,
                    0, new Point());
            //Core.rectangle(segments, boundingRect.tl(), boundingRect.br(), colour, 2,
            //        Core.LINE_8, 0);
            if (radiusEl[0] > 10) {
                Core.circle(segments, centerPoint, (int) radiusEl[0], colour, 2, Core.LINE_8, 0);
            }
            count++;
        }

        backgroundWindow.imShow(segments);
        return segments;
    }


    /**
     * Sets the background to the current frame.
     */
    protected void setBackground() {
        capture.read(background);
    }

    /**
     * Last method of the FrameHandler to be called before program shutdown.
     */
    public void cleanUp() {
        frameWindow.destroyWindow();
        foregroundWindow.destroyWindow();
        capture.release();
    }
}