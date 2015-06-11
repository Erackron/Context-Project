package nl.tudelft.contextproject.imageprocessing.framehandlers;

import nl.tudelft.contextproject.imageprocessing.gui.NamedWindow;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class FrameHandlerTest {
    static {
        nu.pattern.OpenCV.loadShared();
    }
    @Mock
    protected VideoCapture videoCapture;
    @Mock
    protected NamedWindow testWindow;
    @Mock
    protected Mat testMat;
    protected FrameHandler frameHandler;

    @Before
    public void setup() {
        doReturn(true).when(videoCapture).read(any());
        frameHandler = new FrameHandler(videoCapture, testWindow);

        frameHandler.foreground = testMat;
        frameHandler.background = testMat;
        frameHandler.current = testMat;
        frameHandler.edges = testMat;
    }

    @Test
    public void testSetBackground() {
        Mat background = mock(Mat.class);

        frameHandler.background = background;
        frameHandler.setBackground();

        verify(videoCapture, times(1)).read(background);
    }

    @Test
    public void testCleanup() {
        frameHandler.cleanUp();

        verify(testWindow, times(1)).destroyWindow();
        verify(testMat, times(4)).release();
    }
}
