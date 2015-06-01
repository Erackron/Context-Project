package nl.tudelft.contextproject.imageprocessing.gui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.awt.Image;
import java.awt.image.ImageObserver;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


@RunWith(MockitoJUnitRunner.class)
public class ImagePanelTest {
    @Spy
    ImagePanel imagePanel;

    @Test
    public void testSetImage() {
        Image image = mock(Image.class);
        doReturn(10).when(image).getHeight(any(ImageObserver.class));
        doReturn(5).when(image).getWidth(any(ImageObserver.class));

        imagePanel.setImage(image);

        assertEquals("Check that the height of the panel matches the image", 10,
                imagePanel.getHeight());
        assertEquals("Check that the width of the panel matches the image", 5,
                imagePanel.getWidth());

        assertEquals("Check that the preferred height of the panel matches the image", 10,
                imagePanel.getPreferredSize().height);
        assertEquals("Check that the preferred width of the panel matches the image", 5,
                imagePanel.getPreferredSize().width);

        assertEquals("Check that the minimum height of the panel matches the image", 10,
                imagePanel.getMinimumSize().height);
        assertEquals("Check that the minimum width of the panel matches the image", 5,
                imagePanel.getMinimumSize().width);

        assertEquals("Check that the maximum height of the panel matches the image", 10,
                imagePanel.getMaximumSize().height);
        assertEquals("Check that the maximum width of the panel matches the image", 5,
                imagePanel.getMaximumSize().width);
    }
}
