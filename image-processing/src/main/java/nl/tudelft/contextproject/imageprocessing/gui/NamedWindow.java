package nl.tudelft.contextproject.imageprocessing.gui;

import org.opencv.core.Mat;

import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.JFrame;

public class NamedWindow extends Thread {
    protected JFrame frame;
    protected ImagePanel panel;
    protected Mat currentMat;
    protected boolean started = false;
    protected volatile boolean running = true;
    protected volatile boolean updateNeeded = false;

    /**
     * Create a new NamedWindow.
     *
     * @param windowName The name of the window
     */
    public NamedWindow(String windowName) {
        this(windowName, new JFrame(windowName));
    }

    /**
     * Create a new NamedWindow using a pre-existing JFrame.
     *
     * @param windowName The name of the window
     * @param frame       The JFrame to use for this window
     */
    public NamedWindow(String windowName, JFrame frame) {
        this.frame = frame;
        this.frame.setName(windowName);
        this.frame.getContentPane().setLayout(new FlowLayout());
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new ImagePanel();
        this.frame.getContentPane().add(panel);
    }

    /**
     * Render an OpenCV image.
     *
     * @param image The image to draw
     */
    public void imShow(Mat image) {
        this.currentMat = image;
        updateNeeded = true;

        if (!started) {
            this.start();
            started = true;
        }
    }

    /**
     * Convert an OpenCV Mat to an Image.
     *
     * @param m The Mat to convert
     * @return The converted image
     */
    public static Image toBufferedImage(Mat m) {
        // Check if image is grayscale or color
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        // Transfer bytes from Mat to BufferedImage
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] bytes = new byte[bufferSize];
        m.get(0, 0, bytes); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(bytes, 0, targetPixels, 0, bytes.length);
        return image;
    }

    /**
     * Starts this window thread and makes the window visible.
     */
    public void run() {
        frame.setVisible(true);
        while (running) {
            if (updateNeeded) {
                panel.setImage(toBufferedImage(currentMat));
                panel.repaint();
                frame.pack();
                updateNeeded = false;
            }
        }
        frame.setVisible(false);
    }

    /**
     * Destroy the window.
     */
    public void destroyWindow() {
        running = false;
        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
    }
}

