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
    protected Pixmap painting2;
    protected int pixel2;
    @Mock
    protected Pixmap painting3;
    protected int pixel3;
    @Mock
    protected Pixmap newPainting;
    protected int newPixel;
    @Mock
    protected Pixmap newPainting2;
    protected int newPixel2;
    @Mock
    protected Pixmap newPainting3;
    protected int newPixel3;
    @Mock
    protected Texture texture;
    @Mock
    protected Texture texture2;
    @Mock
    protected Texture texture3;
    protected DrawablePixmap drawablePixmap;
    protected DrawablePixmap drawablePixmap2;
    protected DrawablePixmap drawablePixmap3;

    @Before
    public void setUp() {
        pixel = Colour.RED.getPixelValue();
        newPixel = Colour.YELLOW.getPixelValue();
        pixel2 = Colour.BLUE.getPixelValue();
        newPixel2 = Colour.ERASER.getPixelValue();
        pixel3 = Colour.ORANGE.getPixelValue();
        newPixel3 = Colour.RED.getPixelValue();

        drawablePixmap = new DrawablePixmap(painting, newPainting, texture);
        drawablePixmap2 = new DrawablePixmap(painting2, newPainting2, texture2);
        drawablePixmap3 = new DrawablePixmap(painting3, newPainting3, texture3);
        when(painting.getPixel(0, 0)).thenReturn(pixel);
        when(newPainting.getPixel(0, 0)).thenReturn(newPixel);
        when(painting2.getPixel(0, 0)).thenReturn(pixel2);
        when(newPainting2.getPixel(0, 0)).thenReturn(newPixel2);
        when(painting3.getPixel(0, 0)).thenReturn(pixel3);
        when(newPainting3.getPixel(0, 0)).thenReturn(newPixel3);
    }

    @Test
    public void constructorTest() {
        verify(texture).bind();
    }

    @Test
    public void blendTest() {
        drawablePixmap.blend(0, 0, 1, 1);
        verify(newPainting).setColor(Colour.ORANGE.getLibgdxColor());
        verify(newPainting).drawPixel(0, 0);
    }

    @Test
    public void blendEraserTest() {
        drawablePixmap2.blend(0, 0, 1, 1);
        verify(newPainting2).setColor(Colour.WHITE.getLibgdxColor());
        verify(newPainting2).drawPixel(0, 0);
    }

    @Test
    public void blendExceptionTest() {
        drawablePixmap3.blend(0, 0, 1, 1);
        verify(newPainting3).setColor(Colour.ORANGE.getLibgdxColor());
        verify(newPainting3).drawPixel(0, 0);
    }

    @Test
    public void updateTrueTest() {
        drawablePixmap.updateNeeded = true;
        drawablePixmap.update(0, 0, 1, 1);
        verify(painting).drawPixmap(newPainting, 0, 0);
        verify(texture).draw(newPainting, 0, 0);
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
        verify(painting).dispose();
        verify(newPainting).dispose();
        verify(texture).dispose();
    }

    @Test
    public void drawLineTest() {
        drawablePixmap.drawLine(new Vector2(1, 1), new Vector2(2, 2));
        verify(newPainting).drawLine(1, Constants.CAM_HEIGHT - 1, 2, Constants.CAM_HEIGHT
                - 2);
        assertTrue(drawablePixmap.updateNeeded);
    }

    @Test
    public void drawTriangleTest() {
        Vector2 corner1 = new Vector2(1, 1);
        Vector2 corner2 = new Vector2(1, 2);
        Vector2 corner3 = new Vector2(2, 1);

        drawablePixmap.drawTriangle(corner1, corner2, corner3);
        verify(newPainting).fillTriangle(1, 749, 1, 748, 2, 749);
        assertTrue(drawablePixmap.updateNeeded);
    }

    @Test
    public void drawCircleTest() {
        Vector2 center = new Vector2(1f, 1f);
        float radius = 12f;

        drawablePixmap.drawCircle(center, radius);
        verify(newPainting).fillCircle(1, 749, 12);
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
