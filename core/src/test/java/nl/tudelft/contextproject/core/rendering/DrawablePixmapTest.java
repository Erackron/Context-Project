package nl.tudelft.contextproject.core.rendering;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.Colour;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DrawablePixmapTest {
    @Mock
    protected Pixmap painting;
    protected int pixel;
    @Mock
    protected Pixmap newPainting;
    protected int newPixel;
    @Mock
    protected Texture texture;
    protected DrawablePixmap drawablePixmap;

    @Before
    public void setUp() {
        pixel = Colour.RED.getPixelValue();
        newPixel = Colour.YELLOW.getPixelValue();

        drawablePixmap = new DrawablePixmap(painting, newPainting, texture);
        when(painting.getPixel(0, 0)).thenReturn(pixel);
        when(newPainting.getPixel(0, 0)).thenReturn(newPixel);
    }

    @Test
    public void constructorTest() {
        assertTrue(drawablePixmap != null);
    }

    @Test
    public void blendTest() {
        drawablePixmap.blend(0, 0, 1, 1);
        verify(newPainting, times(1)).setColor(Colour.ORANGE.getLibgdxColor());
        verify(newPainting, times(1)).drawPixel(0, 0);
    }

    @Test
    public void updateTrueTest() {
        drawablePixmap.updateNeeded = true;
        drawablePixmap.update(0, 0, 1, 1);
        verify(painting, times(1)).drawPixmap(newPainting, 0, 0);
        verify(texture, times(1)).draw(newPainting, 0, 0);
        assertFalse(drawablePixmap.updateNeeded);
    }

    @Test
    public void updateFalseTest() {
        drawablePixmap.updateNeeded = false;
        drawablePixmap.update(0, 0, 1, 1);
        verify(painting, never()).drawPixmap(newPainting, 0, 0);
        verify(texture, never()).draw(newPainting, 0, 0);
        assertFalse(drawablePixmap.updateNeeded);
    }

    @Test
    public void disposeTest() {
        drawablePixmap.dispose();
        verify(painting, times(1)).dispose();
        verify(newPainting, times(1)).dispose();
        verify(texture, times(1)).dispose();
    }

    @Test
    public void drawLineTest() {
        drawablePixmap.drawLine(new Vector2(1, 1), new Vector2(2, 2));
        verify(newPainting, times(1)).drawLine(1, Constants.CAM_HEIGHT - 1, 2, Constants.CAM_HEIGHT
                - 2);
        assertTrue(drawablePixmap.updateNeeded);
    }

    @Test
    public void drawTriangleTest() {

        Vector2 corner1 = new Vector2(1, 1);
        Vector2 corner2 = new Vector2(1, 2);
        Vector2 corner3 = new Vector2(2, 1);

        drawablePixmap.drawTriangle(corner1, corner2, corner3);
        verify(newPainting, times(1)).fillTriangle((int) corner1.x,
                (int) (Constants.CAM_HEIGHT - Math.min(corner1.y, Constants.CAM_HEIGHT)),
                (int) corner2.x,
                (int) (Constants.CAM_HEIGHT - Math.min(corner2.y, Constants.CAM_HEIGHT)),
                (int) corner3.x,
                (int) (Constants.CAM_HEIGHT - Math.min(corner3.y, Constants.CAM_HEIGHT)));
        assertTrue(drawablePixmap.updateNeeded);
    }

    @Test
    public void getPaintingTest() {
        assertEquals(drawablePixmap.getPainting(), painting);
    }

    @Test
    public void setPaintingTest() {
        drawablePixmap.setPainting(painting);
        assertEquals(drawablePixmap.painting, painting);
    }

    @Test
    public void getNewPaintingTest() {
        assertEquals(drawablePixmap.getNewPainting(), newPainting);
    }

    @Test
    public void setNewPaintingTest() {
        drawablePixmap.setNewPainting(newPainting);
        assertEquals(drawablePixmap.newPainting, newPainting);
    }

    @Test
    public void getCanvasTest() {
        assertEquals(drawablePixmap.getCanvas(), texture);
    }
}
