package nl.tudelft.contextproject.imageprocessing.gui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.opencv.core.Mat;

import java.awt.image.BufferedImage;
import javax.swing.JFrame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class NamedWindowTest {
    @Spy
    protected NamedWindow namedWindow = new NamedWindow("TestWindow");

    @Test
    public void testImShowThreadStart() {
        namedWindow.started = false;
        namedWindow.updateNeeded = false;

        doNothing().when(namedWindow).start();
        Mat mat = mock(Mat.class);
        namedWindow.imShow(mat);

        assertTrue("Check that the argument of imShow is set as currentMat",
                mat == namedWindow.currentMat);
        assertTrue("Check that the window thread was marked as started", namedWindow.started);
        assertTrue("Check that the window needs an update", namedWindow.updateNeeded);
    }

    @Test
    public void testImShowNoThreadStartIfStarted() {
        namedWindow.started = true;
        namedWindow.updateNeeded = false;

        namedWindow.imShow(mock(Mat.class));

        verify(namedWindow, times(0)).start();
        assertTrue("Check that the window needs an update", namedWindow.updateNeeded);
    }

    @Test
    public void testDestroyWindow() {
        namedWindow.running = true;
        namedWindow.frame = spy(JFrame.class);

        namedWindow.destroyWindow();

        assertFalse(namedWindow.running);
    }

    @Test
    public void testToBufferedImageGreyScale() {
        Mat mat = mock(Mat.class);
        when(mat.channels()).thenReturn(1);
        when(mat.cols()).thenReturn(2);
        when(mat.rows()).thenReturn(3);
        doReturn(0).when(mat).get(anyInt(), anyInt(), any(byte[].class));

        BufferedImage image = (BufferedImage) NamedWindow.toBufferedImage(mat);
        assertEquals("Check if the image type is grayscale", BufferedImage.TYPE_BYTE_GRAY,
                image.getType());
        assertEquals("Check if width is the same as the mat width", 2, image.getWidth(null));
        assertEquals("Check if height is the same as the mat height", 3, image.getHeight(null));
    }

    @Test
    public void testToBufferedImageColour() {
        Mat mat = mock(Mat.class);
        when(mat.channels()).thenReturn(3);
        when(mat.cols()).thenReturn(4);
        when(mat.rows()).thenReturn(5);
        doReturn(0).when(mat).get(anyInt(), anyInt(), any(byte[].class));

        BufferedImage image = (BufferedImage) NamedWindow.toBufferedImage(mat);
        assertEquals("Check if the image type is grayscale", BufferedImage.TYPE_3BYTE_BGR,
                image.getType());
        assertEquals("Check if width is the same as the mat width", 4, image.getWidth(null));
        assertEquals("Check if height is the same as the mat height", 5, image.getHeight(null));
    }
}
