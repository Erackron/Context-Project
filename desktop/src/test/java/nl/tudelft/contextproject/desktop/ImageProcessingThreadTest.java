package nl.tudelft.contextproject.desktop;

import nl.tudelft.contextproject.imageprocessing.ImageProcessing;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ImageProcessingThreadTest {
    @Mock
    protected ImageProcessingThread processingThread;
    @Mock
    protected ImageProcessing imageProcessing;
    @Mock
    protected ExitListener exitListener;

    @Before
    public void setup() {
        processingThread.imageProcessingInstance = imageProcessing;
        processingThread.exitListeners = new HashSet<>();
    }

    @Test
    public void testStop() {
        doCallRealMethod().when(processingThread).stop();

        processingThread.running = true;
        processingThread.stop();
        assertFalse(processingThread.running);
    }

    @Test
    public void testRunNoException() throws Exception {
        doCallRealMethod().when(processingThread).run();

        processingThread.running = true;
        doAnswer(invocation -> processingThread.running = false).when(imageProcessing).loop();

        processingThread.run();

        verify(imageProcessing, times(1)).loop();
    }

    @Test
    public void testRunWithException() throws Exception {
        doCallRealMethod().when(processingThread).run();

        processingThread.running = true;

        Exception exception = mock(Exception.class);
        doThrow(exception).when(imageProcessing).loop();
        doNothing().when(exception).printStackTrace();

        processingThread.run();

        verify(imageProcessing, times(1)).loop();
        assertFalse(processingThread.running);
    }

    @Test
    public void testOnExit() {
        doCallRealMethod().when(processingThread).onExit();
        doCallRealMethod().when(processingThread).addExitListener(any());
        processingThread.addExitListener(exitListener);

        processingThread.onExit();

        verify(imageProcessing, times(1)).cleanUp();
        verify(exitListener, times(1)).onExit();
    }

    @Test
    public void testOnExitNullImageProcessing() {
        doCallRealMethod().when(processingThread).onExit();
        processingThread.imageProcessingInstance = null;

        processingThread.onExit();
    }

    @Test
    public void testAddExitListener() {
        doCallRealMethod().when(processingThread).addExitListener(any());
        processingThread.addExitListener(exitListener);

        assertTrue(processingThread.exitListeners.contains(exitListener));
    }

    @Test
    public void testRemoveExitListener() {
        doCallRealMethod().when(processingThread).removeExitListener(any());
        processingThread.exitListeners.add(exitListener);

        processingThread.removeExitListener(exitListener);

        assertFalse(processingThread.exitListeners.contains(exitListener));
    }


}
