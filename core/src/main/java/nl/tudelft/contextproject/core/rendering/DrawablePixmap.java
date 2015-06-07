package nl.tudelft.contextproject.core.rendering;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import nl.tudelft.contextproject.core.config.Constants;
import nl.tudelft.contextproject.core.entities.Colour;

import java.util.Arrays;

/**
 * This class is a wrapper for Pixmap and Texture to enable storing the drawing of the players.
 */
@Data
public class DrawablePixmap implements Disposable {

    protected Pixmap painting;
    protected Pixmap newPainting;
    protected final Texture canvas;

    @Setter(AccessLevel.NONE)
    protected boolean updateNeeded = false;

    /**
     * Creates a DrawablePixmap object that wraps two actual pixmaps.
     * @param painting The Pixmap that will store all of the painting.
     * @param newPainting The Pixmap that will store only recent painting.
     * @param canvas The texture that will be drawn containing all the blended painting
     */
    public DrawablePixmap(Pixmap painting, Pixmap newPainting, Texture canvas) {
        this.painting = painting;
        this.newPainting = newPainting;
        this.canvas = canvas;
        canvas.bind();
    }

    /**
     * Draws a line between two Vector2 coordinates using the currently set colour.
     * The coordinates are converted from the default coordinate system (0,0 is bottom left) to the
     * pixmap coordinate system (0,0 is top left) before being drawn.
     *
     * @param start The first point
     * @param end   The second point
     */
    public void drawLine(Vector2 start, Vector2 end) {
        drawLine((int) start.x, (int) (Constants.CAM_HEIGHT - start.y),
                (int) end.x, (int) (Constants.CAM_HEIGHT - end.y));
    }

    /**
     * Draws a line between the given coordinates using the currently set colour.
     *
     * @param x1 The x-coodinate of the first point
     * @param y1 The y-coordinate of the first point
     * @param x2 The x-coordinate of the second point
     * @param y2 The y-coordinate of the second point
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        newPainting.drawLine(x1, y1, x2, y2);
        updateNeeded = true;
    }

    /**
     * Draw a triangle on this pixmap.
     * The coordinates are converted from the default coordinate system (0,0 is bottom left) to the
     * pixmap coordinate system (0,0 is top left) before being drawn.
     *
     * @param corner1 The first corner coordinates
     * @param corner2 The second corner coordinates
     * @param corner3 The third corner coordinates
     */
    public void drawTriangle(Vector2 corner1, Vector2 corner2, Vector2 corner3) {
        drawTriangle((int) corner1.x,
                (int) (Constants.CAM_HEIGHT - Math.min(corner1.y, Constants.CAM_HEIGHT)),
                (int) corner2.x,
                (int) (Constants.CAM_HEIGHT - Math.min(corner2.y, Constants.CAM_HEIGHT)),
                (int) corner3.x,
                (int) (Constants.CAM_HEIGHT - Math.min(corner3.y, Constants.CAM_HEIGHT)));
    }

    /**
     * Fills a triangle using the given points
     * @param x The x-coordinate for the first point.
     * @param y The y-coordinate for the first point.
     * @param x1 The x-coordinate for the second point.
     * @param i The y-coordinate for the second point.
     * @param x2 The x-coordinate for the third point.
     * @param i1 The y-coordinate for the third point.
     */
    private void drawTriangle(int x, int y, int x1, int i, int x2, int i1) {
        newPainting.fillTriangle(x, y, x1, i, x2, i1);
        updateNeeded = true;
    }

    /**
     * Draws a circle on this pixmap
     * @param center The center of the circle.
     * @param radius The radius of the circle.
     */
    public void drawCircle(Vector2 center, float radius) {
        drawCircle((int) center.x ,
                (int) (Constants.CAM_HEIGHT - Math.min(center.y, Constants.CAM_HEIGHT)),
                (int) radius);
    }

    /**
     * Fills a circle using the given point and radius.
     * @param x The x-coordinate for the center.
     * @param y The y-coordinate for the center.
     * @param radius The radius of the circle.
     */
    private void drawCircle(int x, int y, int radius) {
        newPainting.fillCircle(x, y, radius);
        updateNeeded = true;
    }


    /**
     * Redraw the painting onto the canvas if needed.
     */
    public void update(int x, int y, int width, int height) {
        if (updateNeeded) {
            blend(x, y, width, height);
            painting.drawPixmap(newPainting, x, y);
            canvas.draw(newPainting, x, y);
            updateNeeded = false;
        }
    }

    /**
     * Blend pixels together to combine colours.
     * @param x The x coordinate of where to start.
     * @param y The y coordinate of where to start
     * @param width The width of the rectangle that needs to be blended.
     * @param height The height of the rectangle that needs to be blended.
     */
    public void blend(int x, int y, int width, int height) {
        for (int i = x; i < width; i++) {
            for (int j = y; j < height; j++) {
                int newPixel = newPainting.getPixel(i, j);
                int oldPixel = painting.getPixel(i, j);

                if (newPixel != 0) {
                    Colour first = Colour.getColour(newPixel);
                    if (first.getPixelValue() == 2139062271) {
                        newPainting.setColor(Colour.WHITE.getLibgdxColor());
                        newPainting.drawPixel(i, j);
                    } else if (newPixel != oldPixel && oldPixel != 0) {
                        Colour second = Colour.getColour(oldPixel);
                        Colour blend = Colour.combine(Arrays.asList(first, second));
                        if (blend.getPixelValue() == -5394945) {
                            blend = second;
                        }
                        newPainting.setColor(blend.getLibgdxColor());
                        newPainting.drawPixel(i, j);
                    }
                }
            }
        }
    }

    /**
     * Disposes the Pixmap and Texture objects.
     */
    @Override
    public void dispose() {
        painting.dispose();
        newPainting.dispose();
        canvas.dispose();
    }
}
